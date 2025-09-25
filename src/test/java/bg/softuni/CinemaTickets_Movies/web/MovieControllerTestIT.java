package bg.softuni.CinemaTickets_Movies.web;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.*;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MovieControllerIT {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieClassRepository movieClassRepository;

    @Autowired
    private BookingTimeRepository bookingTimeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMovieClassToRepo();
        movieRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
        movieClassRepository.deleteAll();
    }

    @Test
    void testCreateMovie() throws Exception {
        AddMovieDto addDto = createMovie();

        MvcResult result = mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        int id = JsonPath.read(body, "$.id");

        Optional<Movie> optMovie = movieRepository.findById((long) id);
        Assertions.assertTrue(optMovie.isPresent());
        Assertions.assertEquals("Initial Movie", optMovie.get().getName());
    }

    @Test
    void testGetMovieById() throws Exception {
        AddMovieDto addDto = createMovie();

        MvcResult result = mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/api/movies/movie/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Initial Movie"));
    }

    @Test
    void testGetAllMovies() throws Exception {
        AddMovieDto addDto = createMovie();

        mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(addDto.getName()))
                .andExpect(jsonPath("$[0].description").value(addDto.getDescription()));
    }

    @Test
    void testGetUpcomingMovies() throws Exception {
        AddMovieDto upcomingMovie = createMovie();

        mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upcomingMovie)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/movies/upcoming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(upcomingMovie.getName()))
                .andExpect(jsonPath("$[0].description").value(upcomingMovie.getDescription()));
    }

    @Test
    void testMovieNotFound() throws Exception {
        mockMvc.perform(get("/api/movies/movie/{id}", 5555))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMovieById() throws Exception {
        AddMovieDto addDto = createMovie();

        MvcResult result = mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/api/movies/delete-movie/{id}", id))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(movieRepository.findById((long) id).isEmpty());
    }

    @Test
    void testUpdateMovie() throws Exception {
        AddMovieDto addDto = createMovie();

        MvcResult result = mockMvc.perform(post("/api/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        MovieDto updateDto = getMovieDto(2L);

        mockMvc.perform(put("/api/movies/update-movie/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        Movie updated = movieRepository.findById((long) id).orElseThrow();
        Assertions.assertEquals("Updated Movie", updated.getName());
        Assertions.assertEquals("Updated desc", updated.getDescription());
        Assertions.assertEquals(150, updated.getMovieLength());
        Assertions.assertEquals(MovieClassEnum.B_, updated.getMovieClass().getName());
    }

    @Test
    void testGetBookingTimeByValue() throws Exception {
        BookingTime bookingTime = bookingTimeRepository
                .findByBookingTimeValue(BookingTimeEnum._18_20.getValue())
                .orElseThrow(() -> new RuntimeException("No booking time found in DB"));

        mockMvc.perform(get("/api/movies/bookingTime/{value}", bookingTime.getBookingTimeValue()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingTime.getId()))
                .andExpect(jsonPath("$.bookingTime").value(bookingTime.getBookingTime().name()))
                .andExpect(jsonPath("$.bookingTimeValue").value(bookingTime.getBookingTimeValue()));

    }

    @Test
    void testGetBookingTimeById() throws Exception {
        BookingTime bookingTime = new BookingTime();
        bookingTime.setBookingTime(BookingTimeEnum._19_50);
        bookingTime = bookingTimeRepository.save(bookingTime);

        mockMvc.perform(get("/api/movies/booking-time/{id}", bookingTime.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingTime.getId()))
                .andExpect(jsonPath("$.bookingTime").value("_19_50"));
    }

    private void initMovieClassToRepo() {
        if (movieClassRepository.count() == 0) {
            List<MovieClass> movieClasses = Arrays.stream(MovieClassEnum.values())
                    .map(MovieClass::new)
                    .toList();
            movieClassRepository.saveAll(movieClasses);
        }
    }

    private AddMovieDto createMovie() {
        AddMovieDto addDto = new AddMovieDto();
        addDto.setAudio("Angl.");
        addDto.setDescription("Initial desc");
        addDto.setHallNumber(HallNumber.HALL_2);
        addDto.setImageUrl("https://example.com/test.jpg");
        addDto.setMovieLength(120);
        addDto.setName("Initial Movie");
        addDto.setProjectionFormat(ProjectionFormat.D_2D);
        addDto.setSubtitles("Bulg.");
        addDto.setTrailerUrl("https://youtube.com/embed/test");
        addDto.setMovieClass(MovieClassEnum.C_PLUS);
        addDto.setGenreCategories(List.of(Genre.FANTASY, Genre.HORROR));

        return addDto;
    }

    private MovieDto getMovieDto(long id) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setAudio("Bulgarian");
        movieDto.setDescription("Updated desc");
        movieDto.setHallNumber(HallNumber.HALL_3);
        movieDto.setImageUrl("https://example.com/updated.jpg");
        movieDto.setMovieLength(150);
        movieDto.setName("Updated Movie");
        movieDto.setProjectionFormat(ProjectionFormat.D_3D);
        movieDto.setSubtitles("Eng.");
        movieDto.setTrailerUrl("https://youtube.com/embed/updated");
        movieDto.setMovieClassName("B_");
        movieDto.setGenreCategories(List.of(Genre.ACTION));

        return movieDto;
    }
}

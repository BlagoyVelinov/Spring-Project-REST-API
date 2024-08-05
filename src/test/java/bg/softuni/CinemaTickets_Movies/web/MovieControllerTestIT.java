package bg.softuni.CinemaTickets_Movies.web;

import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.*;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTestIT {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieClassRepository movieClassRepository;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.initMovieClassToRepo();
    }

    @AfterEach
    public void tearDown() {
        this.movieRepository.deleteAll();
        this.movieClassRepository.deleteAll();
    }

    @Test
    public void testGetMovieById() throws Exception {
        Movie testMovie = this.createMovie();
        MvcResult mvcResult = this.mockMvc.perform(post("/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                  "audio": "Angl.",
                                  "description": "Нито звук: Ден първи ни пренася сред оживените улици на Ню Йорк, където ставаме свидетели на ужасяващите първи мигове от колапса на човечеството и потъването на света в тишина...",
                                  "hallNumber":"HALL_2",
                                  "imageUrl":"https://m.media-amazon.com/images/M/MV5BNGZmODU3ZDEtMjQwZC00NTA5LThmNWYtYzk5MmY5ZmM4NGIxXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                                  "movieLength": 120,
                                  "name":"Trap: Day one",
                                  "projectionFormat":"D_2D",
                                  "subtitles":"Bulg.",
                                  "trailerUrl": "https://www.youtube.com/embed/YPY7J-flzE8",
                                  "movieClass": "C_PLUS",
                                  "genreCategories":["FANTASY", "HORROR"]
                                }
                                """)

                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        this.mockMvc
                .perform(get("/movies/movie/{id}", testMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testCreateMovie() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                  "audio": "Angl.",
                                  "description": "Нито звук: Ден първи ни пренася сред оживените улици на Ню Йорк, където ставаме свидетели на ужасяващите първи мигове от колапса на човечеството и потъването на света в тишина...",
                                  "hallNumber":"HALL_2",
                                  "imageUrl":"https://m.media-amazon.com/images/M/MV5BNGZmODU3ZDEtMjQwZC00NTA5LThmNWYtYzk5MmY5ZmM4NGIxXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                                  "movieLength": 120,
                                  "name":"Trap: Day one",
                                  "projectionFormat":"D_2D",
                                  "subtitles":"Bulg.",
                                  "trailerUrl": "https://www.youtube.com/embed/YPY7J-flzE8",
                                  "movieClass": "C_PLUS",
                                  "genreCategories":["FANTASY", "HORROR"]
                                }
                                """)

                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        int id = JsonPath.read(body, "$.id");
        Optional<Movie> optMovie = this.movieRepository.findById((long) id);

        Assertions.assertTrue(optMovie.isPresent());
        Movie movie = optMovie.get();
        Assertions.assertEquals("Trap: Day one", movie.getName());
        Assertions.assertEquals("https://www.youtube.com/embed/YPY7J-flzE8", movie.getTrailerUrl());
    }

    @Test
    public void testMovieNotFound() throws Exception {
        Movie testMovie = this.createMovie();
        this.mockMvc
                .perform(get("/movies/movie/{id}", 5555)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMovieById() throws Exception {

        Movie testMovie = this.createMovie();
        MvcResult mvcResult = this.mockMvc.perform(post("/movies/add-movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                  "audio": "Angl.",
                                  "description": "Нито звук: Ден първи ни пренася сред оживените улици на Ню Йорк, където ставаме свидетели на ужасяващите първи мигове от колапса на човечеството и потъването на света в тишина...",
                                  "hallNumber":"HALL_2",
                                  "imageUrl":"https://m.media-amazon.com/images/M/MV5BNGZmODU3ZDEtMjQwZC00NTA5LThmNWYtYzk5MmY5ZmM4NGIxXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                                  "movieLength": 120,
                                  "name":"Trap: Day one",
                                  "projectionFormat":"D_2D",
                                  "subtitles":"Bulg.",
                                  "trailerUrl": "https://www.youtube.com/embed/YPY7J-flzE8",
                                  "movieClass": "C_PLUS",
                                  "genreCategories":["FANTASY", "HORROR"]
                                }
                                """)

                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        this.mockMvc.perform(delete("/movies/delete-movie/{id}", testMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(this.movieRepository.findById(testMovie.getId()).isEmpty());
    }

    @Test
    public void testAddBookingTimes() throws Exception {
        Movie testMovie = this.createMovie();

        MvcResult mvcResult = this.mockMvc.perform(put("/movies/update-projection-time/{id}", testMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                  "audio": "Angl.",
                                  "description": "Нито звук: Ден първи ни пренася сред оживените улици на Ню Йорк, където ставаме свидетели на ужасяващите първи мигове от колапса на човечеството и потъването на света в тишина...",
                                  "hallNumber":"HALL_2",
                                  "imageUrl":"https://m.media-amazon.com/images/M/MV5BNGZmODU3ZDEtMjQwZC00NTA5LThmNWYtYzk5MmY5ZmM4NGIxXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                                  "movieLength": 120,
                                  "name":"Trap: Day one",
                                  "projectionFormat":"D_2D",
                                  "subtitles":"Bulg.",
                                  "trailerUrl": "https://www.youtube.com/embed/YPY7J-flzE8",
                                  "movieClass": "C_PLUS",
                                  "genreCategories":["FANTASY", "HORROR"],
                                  "bookingTimes": ["_18_20", "_19_50"] 
                                }
                                """)

                ).andExpect(status().isUpgradeRequired())
                .andExpect(header().exists("Location"))
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        int id = JsonPath.read(body, "$.id");
        Optional<Movie> optMovie = this.movieRepository.findById((long) id);

        Assertions.assertTrue(optMovie.isPresent());
        Movie movie = optMovie.get();
        Assertions.assertEquals(BookingTimeEnum._18_20, movie.getBookingTimes().get(0).getBookingTime());
    }

    private Movie createMovie() {
        List<Category> categories = List.of(new Category(Genre.ACTION));
        MovieClass classEnum = new MovieClass(MovieClassEnum.D_);
        return new Movie()
                .setId(1)
                .setAudio("Angl.")
                .setGenreCategories(categories)
                .setMovieClass(classEnum)
                .setDescription("Creating test movie")
                .setName("Test movie")
                .setHallNumber(HallNumber.HALL_2)
                .setMovieLength(120)
                .setSubtitles("Bulg")
                .setImageUrl("testImage")
                .setTrailerUrl("https://www.youtube.com/embed/hJiPAJKjUVg")
                .setProjectionFormat(ProjectionFormat.D_3D);
    }

    private void initMovieClassToRepo() {
        if (this.movieClassRepository.count() == 0) {
            List<MovieClass> movieClasses = Arrays.stream(MovieClassEnum.values())
                    .map(MovieClass::new)
                    .toList();
            this.movieClassRepository.saveAll(movieClasses);
        }
    }
}

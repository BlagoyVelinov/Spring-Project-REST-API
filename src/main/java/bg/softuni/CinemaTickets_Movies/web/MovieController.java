package bg.softuni.CinemaTickets_Movies.web;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final BookingTimeService bookingTimeService;

    @Autowired
    public MovieController(MovieService movieService, BookingTimeService bookingTimeService) {
        this.movieService = movieService;
        this.bookingTimeService = bookingTimeService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(this.movieService.getAllMovies());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieDto>> getUpcomingMovies() {
        return ResponseEntity.ok(this.movieService.getAllUpcomingMovies());
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> createMovie(@RequestBody AddMovieDto addMovieDto) {
        MovieDto movieDto = this.movieService.movieCreate(addMovieDto);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/add-movie/{id}")
                        .buildAndExpand(movieDto.getId())
                        .toUri()
        ).body(movieDto);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable long id) {
        MovieDto movieView = this.movieService.getMovieDtoById(id);
        return ResponseEntity.ok(movieView);
    }

    @PutMapping("/update-movie/{id}")
    public ResponseEntity<MovieDto>updateMovie(@PathVariable("id") long id,
                                                    @RequestBody MovieDto movieDto) {
        this.movieService.changeMovieData(id, movieDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity<MovieDto> deleteMovieById(@PathVariable("id") long id) {
        this.movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/booking-time/{id}")
    public ResponseEntity<BookingTime> getBookingTime(@PathVariable("id") long id) {
        BookingTime bookingTime = this.bookingTimeService.getBookingTimeById(id);
        return ResponseEntity.ok(bookingTime);
    }

    @GetMapping("/bookingTime/{value}")
    public ResponseEntity<BookingTime> getBookingTimeByValue(@PathVariable("value") String value) {
        BookingTime bookingTime = this.bookingTimeService.getBookingTimeByValue(value);
        return ResponseEntity.ok(bookingTime);
    }
}

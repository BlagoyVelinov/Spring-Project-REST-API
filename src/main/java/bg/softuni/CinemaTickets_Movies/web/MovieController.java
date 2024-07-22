package bg.softuni.CinemaTickets_Movies.web;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(this.movieService.getAllMovies());
    }
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> createMovie(@RequestBody AddMovieDto addMovieDto) {
        MovieDto movieDto = this.movieService.movieCreate(addMovieDto);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/add=movie/{id}")
                        .buildAndExpand(movieDto.getId())
                        .toUri()
        ).body(movieDto);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable long id) {
        MovieDto movieView = this.movieService.getMovieDtoById(id);
        return ResponseEntity.ok( movieView);
    }

    @PutMapping("/update-projection-time/{id}")
    public ResponseEntity<MovieDto>updateProjection(@PathVariable("id") long id,
                                                    @RequestBody BookingTimeDto bookingTimeDto) {
        this.movieService.addBookingTimes(id, bookingTimeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity<MovieDto> deleteMovieById(@PathVariable("id") long id) {
        this.movieService.deleteMovieById(id);
        return ResponseEntity.ok().build();
    }
}

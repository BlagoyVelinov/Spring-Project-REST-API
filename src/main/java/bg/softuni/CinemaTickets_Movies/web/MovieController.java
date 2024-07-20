package bg.softuni.CinemaTickets_Movies.web;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(AddMovieDto addMovieDto) {
        this.movieService.movieCreate(addMovieDto);
        return ResponseEntity.ok().build();
    }
}

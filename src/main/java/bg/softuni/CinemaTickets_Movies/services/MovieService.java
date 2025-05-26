package bg.softuni.CinemaTickets_Movies.services;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;

import java.util.List;

public interface MovieService {
    MovieDto movieCreate(AddMovieDto createMovie);

    Movie getMovieById(long id);

    List<MovieDto> getAllMovies();

    List<MovieDto> getAllUpcomingMovies();

    void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto);
    void deleteMovieById(long movieId);
    MovieDto getMovieDtoById(long movieId);
}

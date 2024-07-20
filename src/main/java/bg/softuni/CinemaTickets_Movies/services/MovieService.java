package bg.softuni.CinemaTickets_Movies.services;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;

public interface MovieService {
    void movieCreate(AddMovieDto createMovie);

//    List<Movie> getAllMovies();
//
//    Movie getMovieById(long id);
//
//    List<MovieDto> getAllMoviesView();
//    List<MovieDto> getAllMoviesViewWithBookingTimes();
//
//    void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto);
//    void deleteMovieById(long movieId);
//
//    MovieDto getMovieViewById(long movieId);
}

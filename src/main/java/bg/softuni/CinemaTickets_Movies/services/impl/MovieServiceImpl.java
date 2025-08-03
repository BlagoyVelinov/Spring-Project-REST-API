package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import bg.softuni.CinemaTickets_Movies.services.CategoryService;
import bg.softuni.CinemaTickets_Movies.services.MovieClassService;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import bg.softuni.CinemaTickets_Movies.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final MovieClassService movieClassService;
    private final BookingTimeService bookingTimeService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryService categoryService,
                            MovieClassService movieClassService, BookingTimeService bookingTimeService) {
        this.movieRepository = movieRepository;
        this.categoryService = categoryService;

        this.movieClassService = movieClassService;
        this.bookingTimeService = bookingTimeService;
    }

    @Override
    public Movie getMovieById(long id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("--->Movie not found!<---"));
    }

    @Override
    public MovieDto movieCreate(AddMovieDto createMovie) {
        Movie movie = this.mapMovieDtoToMovie(createMovie);
        this.movieRepository.save(movie);
        return this.mapMovieToMovieDto(movie);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movieList = this.movieRepository.findAll();
        return movieList.stream()
                .map(this::mapMovieToMovieDto)
                .toList();
    }

    @Override
    public List<MovieDto> getAllUpcomingMovies() {
        List<Movie> upcomingMovieList = this.movieRepository.findAllMoviesByBookingTimesEmpty();
        return upcomingMovieList.stream()
                .map(this::mapMovieToMovieDto)
                .toList();
    }

    @Override
    public void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto) {
        Movie movie = this.getMovieById(movieId);
        List<BookingTime> bookingTimes = this.bookingTimeService.getBookingTimesByStartTime(bookingTimeDto);
        movie.setBookingTimes(bookingTimes);
        this.movieRepository.save(movie);
    }

    @Override
    public void deleteMovieById(long movieId) {
        this.movieRepository.deleteById(movieId);
    }

    @Override
    public MovieDto getMovieDtoById(long movieId) {
        Movie movie = this.getMovieById(movieId);
        return this.mapMovieToMovieDto(movie);
    }

    @Override
    public MovieDto changeMovieData(long id, MovieDto movieDto) {
        Movie movie = this.getMovieById(id);
        movie = this.mapChangedMovieDtoToMovie(movie, movieDto);
        this.movieRepository.save(movie);

        return movieDto;
    }

    private MovieDto mapMovieToMovieDto(Movie movie) {
        List<Genre> categories = movie.getGenreCategories()
                .stream()
                .map(Category::getName)
                .toList();

        return new MovieDto()
                .setId(movie.getId())
                .setAudio(movie.getAudio())
                .setGenreCategories(categories)
                .setMovieClass(movie.getMovieClass())
                .setDescription(movie.getDescription())
                .setName(movie.getName())
                .setHallNumber(movie.getHallNumber())
                .setMovieLength(movie.getMovieLength())
                .setSubtitles(movie.getSubtitles())
                .setImageUrl(movie.getImageUrl())
                .setTrailerUrl(movie.getTrailerUrl())
                .setProjectionFormat(movie.getProjectionFormat())
                .setBookingTimes(movie.getBookingTimes()
                        .stream()
                        .map(BookingTime::getBookingTimeValue)
                        .collect(Collectors.toList()));
    }

    private Movie mapMovieDtoToMovie(AddMovieDto addMovie) {
        List<Category> categories = this.categoryService.getCategoriesByGenre(addMovie.getGenreCategories());
        MovieClass movieClass = this.movieClassService.getMovieClassByName(addMovie.getMovieClass());

        return new Movie()
                .setAudio(addMovie.getAudio())
                .setGenreCategories(categories)
                .setMovieClass(movieClass)
                .setDescription(addMovie.getDescription())
                .setName(addMovie.getName())
                .setHallNumber(addMovie.getHallNumber())
                .setMovieLength(addMovie.getMovieLength())
                .setSubtitles(addMovie.getSubtitles())
                .setImageUrl(addMovie.getImageUrl())
                .setTrailerUrl(addMovie.getTrailerUrl())
                .setProjectionFormat(addMovie.getProjectionFormat());
    }

    private Movie mapChangedMovieDtoToMovie(Movie movie, MovieDto movieDto) {
        List<Category> categories = this.categoryService.getCategoriesByGenre(movieDto.getGenreCategories());
        List<BookingTime> bookingTimes = this.bookingTimeService.getBookingTimesByValues(movieDto.getBookingTimes());

        return movie
                .setAudio(movieDto.getAudio())
                .setGenreCategories(categories)
                .setMovieClass(movieDto.getMovieClass())
                .setDescription(movieDto.getDescription())
                .setName(movieDto.getName())
                .setHallNumber(movieDto.getHallNumber())
                .setMovieLength(movieDto.getMovieLength())
                .setSubtitles(movieDto.getSubtitles())
                .setImageUrl(movieDto.getImageUrl())
                .setTrailerUrl(movieDto.getTrailerUrl())
                .setProjectionFormat(movieDto.getProjectionFormat())
                .setBookingTimes(bookingTimes);
    }
}

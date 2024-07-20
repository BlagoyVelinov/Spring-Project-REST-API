package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.dtos.MovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import bg.softuni.CinemaTickets_Movies.repositories.CategoryRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieClassRepository movieClassRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository,
                            MovieClassRepository movieClassRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.movieClassRepository = movieClassRepository;
    }

    @Override
    public Movie getMovieById(long id) {
        return this.movieRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("--->Movie not found!<---"));
    }

    @Override
    public void movieCreate(AddMovieDto createMovie) {
        Movie movie = this.mapMovieDtoToMovie(createMovie);

        this.movieRepository.save(movie);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movieList = this.movieRepository.findAll();
        return movieList.stream()
                .map(this::mapMovieToMovieDto)
                .toList();
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
                .setBookingTimes(movie.getBookingTimes());
    }
    private Movie mapMovieDtoToMovie(AddMovieDto addMovie) {
        List<Category> categories = this.categoryRepository.findAllByNameIn(addMovie.getGenreCategories());
        MovieClass movieClass = this.movieClassRepository.findByName(addMovie.getMovieClass());

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
}

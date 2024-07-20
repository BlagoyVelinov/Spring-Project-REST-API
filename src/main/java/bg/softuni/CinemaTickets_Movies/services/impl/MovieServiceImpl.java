package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
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
    public void movieCreate(AddMovieDto createMovie) {
        Movie movie = this.mapMovieDtoToMovie(createMovie);

        this.movieRepository.save(movie);
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

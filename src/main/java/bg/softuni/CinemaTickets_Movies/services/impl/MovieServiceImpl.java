package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.AddMovieDto;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.repositories.CategoryRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
import bg.softuni.CinemaTickets_Movies.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieClassRepository movieClassRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository,
                            MovieClassRepository movieClassRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.movieClassRepository = movieClassRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void movieCreate(AddMovieDto createMovie) {
        Movie movie = this.mapMovieDtoToMovie(createMovie);

        this.movieRepository.save(movie);
    }

    private Movie mapMovieDtoToMovie(AddMovieDto addMovie) {
        List<Category> categories = this.categoryRepository.findAllByNameIn(addMovie.getGenreCategories());
        MovieClass movieClass = this.movieClassRepository.findByName(addMovie.getMovieClass());

        Movie movie = this.modelMapper.map(addMovie, Movie.class);
        movie.setGenreCategories(categories)
                .setMovieClass(movieClass);
        return movie;
    }
}

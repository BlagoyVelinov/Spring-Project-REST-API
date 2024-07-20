package bg.softuni.CinemaTickets_Movies.validation;

import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
import bg.softuni.CinemaTickets_Movies.validation.annotation.UniqueMovieName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueMovieNameValidator implements ConstraintValidator<UniqueMovieName, String> {

    private final MovieRepository movieRepository;

    public UniqueMovieNameValidator(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void initialize(UniqueMovieName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String movieName, ConstraintValidatorContext context) {
        return this.movieRepository.findByName(movieName) == null;
    }
}

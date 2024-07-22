package bg.softuni.CinemaTickets_Movies.services;

import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.MovieClassEnum;

public interface MovieClassService {
    MovieClass getMovieClassByName(MovieClassEnum movieClass);
}

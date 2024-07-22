package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.MovieClassEnum;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.services.MovieClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieClassServiceImpl implements MovieClassService {

    private final MovieClassRepository movieClassRepository;

    @Autowired
    public MovieClassServiceImpl(MovieClassRepository movieClassRepository) {
        this.movieClassRepository = movieClassRepository;
    }

    @Override
    public MovieClass getMovieClassByName(MovieClassEnum movieClass) {
        return this.movieClassRepository.findByName(movieClass);
    }
}

package bg.softuni.CinemaTickets_Movies.repositories;

import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByName(String movieName);
    List<Movie> findAllMoviesByBookingTimesEmpty();
}

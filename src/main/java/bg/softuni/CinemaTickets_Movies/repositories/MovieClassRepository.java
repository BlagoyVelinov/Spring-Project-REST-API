package bg.softuni.CinemaTickets_Movies.repositories;

import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.MovieClassEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieClassRepository extends JpaRepository<MovieClass, Long> {
    MovieClass findByName(MovieClassEnum movieClass);
}

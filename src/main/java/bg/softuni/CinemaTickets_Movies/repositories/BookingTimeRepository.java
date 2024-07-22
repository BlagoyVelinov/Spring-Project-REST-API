package bg.softuni.CinemaTickets_Movies.repositories;

import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.enums.BookingTimeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingTimeRepository extends JpaRepository<BookingTime, Long> {
    List<BookingTime> findAllByBookingTimeIn(List<BookingTimeEnum> startMovieTimes);
}

package bg.softuni.CinemaTickets_Movies.services;

import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;

import java.util.List;

public interface BookingTimeService {
    List<BookingTime> getBookingTimesByStartTime(BookingTimeDto bookingTimeDto);
    BookingTime getBookingTimeById(long id);

}

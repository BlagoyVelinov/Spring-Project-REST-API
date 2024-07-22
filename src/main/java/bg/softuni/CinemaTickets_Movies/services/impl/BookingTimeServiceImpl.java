package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingTimeServiceImpl implements BookingTimeService {

    private final BookingTimeRepository bookingTimeRepository;

    @Autowired
    public BookingTimeServiceImpl(BookingTimeRepository bookingTimeRepository) {
        this.bookingTimeRepository = bookingTimeRepository;
    }

    @Override
    public List<BookingTime> getBookingTimesByStartTime(BookingTimeDto bookingTimeDto) {
        return this.bookingTimeRepository.findAllByBookingTimeIn(bookingTimeDto.getStartMovieTimes());
    }
}

package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import bg.softuni.CinemaTickets_Movies.services.exception.ObjectNotFoundException;
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

    @Override
    public List<BookingTime> getBookingTimesByValues(List<String> bookingTimeValues) {
        return this.bookingTimeRepository.findAllByBookingTimeValueIn(bookingTimeValues);
    }

    @Override
    public BookingTime getBookingTimeById(long id) {
        return this.bookingTimeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Booking time is not found!"));
    }

    @Override
    public BookingTime getBookingTimeByValue(String value) {
        return this.bookingTimeRepository.findByBookingTimeValue(value)
                .orElseThrow(() -> new ObjectNotFoundException("Booking time is not found!"));
    }

}

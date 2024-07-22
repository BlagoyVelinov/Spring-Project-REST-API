package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.dtos.BookingTimeDto;
import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.enums.BookingTimeEnum;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import bg.softuni.CinemaTickets_Movies.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public BookingTime getBookingTimeById(long id) {
        return this.bookingTimeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Booking time is not found!"));
    }

    @Override
    public void initBookingTimesValues() {
        if (this.bookingTimeRepository.count() == 0) {
            List<BookingTime> bookingTimeList = Arrays.stream(BookingTimeEnum.values())
                    .map(BookingTime::new)
                    .toList();

            this.bookingTimeRepository.saveAll(bookingTimeList);
        }
    }
}

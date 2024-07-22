package bg.softuni.CinemaTickets_Movies.utils;

import bg.softuni.CinemaTickets_Movies.services.BookingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstInit implements CommandLineRunner {
    private final BookingTimeService bookingTimeService;

    @Autowired
    public FirstInit(BookingTimeService bookingTimeService) {
        this.bookingTimeService = bookingTimeService;
    }

    @Override
    public void run(String... args) {
        this.bookingTimeService.initBookingTimesValues();
    }
}

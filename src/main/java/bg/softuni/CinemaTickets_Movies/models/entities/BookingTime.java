package bg.softuni.CinemaTickets_Movies.models.entities;


import bg.softuni.CinemaTickets_Movies.models.enums.BookingTimeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_times")
public class BookingTime extends BaseEntity {

    @Column(name = "booking_time")
    @Enumerated(EnumType.STRING)
    private BookingTimeEnum bookingTime;

    public BookingTime() {}

    public BookingTimeEnum getBookingTime() {
        return bookingTime;
    }

    public BookingTime setBookingTime(BookingTimeEnum bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }
}

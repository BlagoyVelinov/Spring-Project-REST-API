package bg.softuni.CinemaTickets_Movies.models.entities;


import bg.softuni.CinemaTickets_Movies.models.enums.BookingTimeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_times")
public class BookingTime extends BaseEntity {

    @Column(name = "booking_time")
    @Enumerated(EnumType.STRING)
    private BookingTimeEnum bookingTime;
    private String bookingTimeValue;

    public BookingTime() {
    }

    public BookingTime(BookingTimeEnum bookingTime) {
        this.setBookingTime(bookingTime);
    }

    public BookingTimeEnum getBookingTime() {
        return bookingTime;
    }

    public BookingTime setBookingTime(BookingTimeEnum bookingTime) {
        this.bookingTime = bookingTime;
        this.setBookingTimeValue(bookingTime);
        return this;
    }
    public String getBookingTimeValue() {
        return bookingTimeValue;
    }

    public BookingTime setBookingTimeValue(String bookingTimeValue) {
        this.bookingTimeValue = bookingTimeValue;
        return this;
    }

    private void setBookingTimeValue(BookingTimeEnum bookingTimeEnum) {
        String value = null;
        switch (bookingTimeEnum) {
            case _10_20,
                    _13_50,
                    _15_50,
                    _16_20,
                    _17_50,
                    _19_50,
                    _11_50,
                    _12_20,
                    _14_20,
                    _18_20,
                    _20_20,
                    _20_50 -> value = bookingTimeEnum.getValue();
        }
        this.bookingTimeValue = value;
    }
}

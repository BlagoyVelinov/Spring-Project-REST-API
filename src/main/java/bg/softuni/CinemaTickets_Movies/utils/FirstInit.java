package bg.softuni.CinemaTickets_Movies.utils;

import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.BookingTimeEnum;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import bg.softuni.CinemaTickets_Movies.models.enums.MovieClassEnum;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.repositories.CategoryRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FirstInit implements CommandLineRunner {
    private final BookingTimeRepository bookingTimeRepository;
    private final CategoryRepository categoryRepository;
    private final MovieClassRepository movieClassRepository;

    @Autowired
    public FirstInit(BookingTimeRepository bookingTimeRepository, CategoryRepository categoryRepository,
                     MovieClassRepository movieClassRepository) {
        this.bookingTimeRepository = bookingTimeRepository;
        this.categoryRepository = categoryRepository;
        this.movieClassRepository = movieClassRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.initBookingTimesInDB();
        this.initCategoriesInDB();
        this.initClassesOfMovieInDB();
    }

    private void initClassesOfMovieInDB() {
        if (this.movieClassRepository.count() == 0) {
            List<MovieClass> movieClasses = Arrays.stream(MovieClassEnum.values())
                    .map(MovieClass::new)
                    .toList();
            this.movieClassRepository.saveAll(movieClasses);
        }
    }

    private void initCategoriesInDB() {
        if (this.categoryRepository.count() == 0) {
            List<Category> categories = Arrays.stream(Genre.values())
                    .map(Category::new)
                    .toList();
            this.categoryRepository.saveAll(categories);
        }
    }

    private void initBookingTimesInDB() {
        if (this.bookingTimeRepository.count() == 0) {
            List<BookingTime> bookingTimes = Arrays.stream(BookingTimeEnum.values())
                    .map(BookingTime::new)
                    .toList();
            this.bookingTimeRepository.saveAll(bookingTimes);
        }
    }
}

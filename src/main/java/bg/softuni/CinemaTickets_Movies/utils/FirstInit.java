package bg.softuni.CinemaTickets_Movies.utils;

import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.entities.Movie;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.*;
import bg.softuni.CinemaTickets_Movies.repositories.BookingTimeRepository;
import bg.softuni.CinemaTickets_Movies.repositories.CategoryRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieClassRepository;
import bg.softuni.CinemaTickets_Movies.repositories.MovieRepository;
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
    private final MovieRepository movieRepository;

    @Autowired
    public FirstInit(BookingTimeRepository bookingTimeRepository, CategoryRepository categoryRepository,
                     MovieClassRepository movieClassRepository, MovieRepository movieRepository) {
        this.bookingTimeRepository = bookingTimeRepository;
        this.categoryRepository = categoryRepository;
        this.movieClassRepository = movieClassRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.initBookingTimesInDB();
        this.initCategoriesInDB();
        this.initClassesOfMovieInDB();
        this.initMoviesInDB();
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

    private void initMoviesInDB() {
        if (this.movieRepository.count() == 0) {
            Movie movie1 = new Movie()
                    .setAudio("Engl")
                    .setDescription("Two years after her move to San Francisco, 13-year-old Riley Andersen is about to enter high school...")
                    .setHallNumber(HallNumber.HALL_5)
                    .setImageUrl("https://m.media-amazon.com/images/M/MV5BYTc1MDQ3NjAtOWEzMi00YzE1LWI2OWUtNjQ0OWJkMzI3MDhmXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg")
                    .setMovieLength(120)
                    .setName("Inside Out 2")
                    .setProjectionFormat(ProjectionFormat.D_3D)
                    .setSubtitles("Bulg")
                    .setTrailerUrl("https://www.youtube.com/embed/I9i_feCLZag")
                    .setMovieClass(this.movieClassRepository.findById(1L).orElseThrow());

            Movie movie2 = new Movie()
                    .setAudio("Engl")
                    .setDescription("Four years after the death of Isabel Aretas, MPD Detective Mike Lowrey marries his physical therapist, Christine...")
                    .setHallNumber(HallNumber.HALL_6)
                    .setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRi9kID45MZjKWQgYyE7mSXHRYc4nRC9cnSV09wM9g2i7SS3S0xP93ZQMnsbkSgca9sA_A&usqp=CAU")
                    .setMovieLength(100)
                    .setName("Bad Boys: Ride Or Die")
                    .setProjectionFormat(ProjectionFormat.D_3D)
                    .setSubtitles("Bulg")
                    .setTrailerUrl("https://www.youtube.com/embed/ZTQyMmz-cQE")
                    .setMovieClass(this.movieClassRepository.findById(4L).orElseThrow());

            Movie movie3 = new Movie()
                    .setAudio("Engl")
                    .setDescription("Alien-Romulus: takes the phenomenally successful Alien franchise back to its roots: While scavenging the deep ends of a derelict space station...")
                    .setHallNumber(HallNumber.HALL_1)
                    .setImageUrl("https://posterspy.com/wp-content/uploads/2024/04/AlienRomulus_Poster02-nightshade-intensity-LOW-V1.jpg")
                    .setMovieLength(140)
                    .setName("Alien: Romulus")
                    .setProjectionFormat(ProjectionFormat.D_4DX)
                    .setSubtitles("Bulg")
                    .setTrailerUrl("https://www.youtube.com/embed/x0XDEhP4MQs")
                    .setMovieClass(this.movieClassRepository.findById(5L).orElseThrow());

            Movie movie4 = new Movie()
                    .setAudio("Bulg.Engl")
                    .setDescription("Всичко в живота на любимия на малки и големи суперзлодей Гру...")
                    .setHallNumber(HallNumber.HALL_3)
                    .setImageUrl("https://www.cinemacity.bg/xmedia-cw/repo/feats/posters/6361D3R.jpg")
                    .setMovieLength(95)
                    .setName("Despicable Me 4")
                    .setProjectionFormat(ProjectionFormat.D_3D)
                    .setSubtitles("Bulg")
                    .setTrailerUrl("https://www.youtube.com/embed/qQlr9-rF32A")
                    .setMovieClass(this.movieClassRepository.findById(1L).orElseThrow());

            Movie movie5 = new Movie()
                    .setAudio("Engl")
                    .setDescription("Trap follows a father and his teen daughter at a pop concert, only to find out something darker...")
                    .setHallNumber(HallNumber.HALL_2)
                    .setImageUrl("https://m.media-amazon.com/images/S/pv-target-images/15fe2e50973554403896b11b63745d3164dff114772db05b1c7e66976118739f.jpg")
                    .setMovieLength(120)
                    .setName("Trap 2024")
                    .setProjectionFormat(ProjectionFormat.D_4DX)
                    .setSubtitles("Bulg")
                    .setTrailerUrl("https://www.youtube.com/embed/hJiPAJKjUVg")
                    .setMovieClass(this.movieClassRepository.findById(5L).orElseThrow());


            List<Movie> movies = List.of(movie1, movie2, movie3, movie4, movie5);
            this.movieRepository.saveAll(movies);

            movie4.getBookingTimes().addAll(List.of(
                    this.bookingTimeRepository.findById(4L).orElseThrow(),
                    this.bookingTimeRepository.findById(5L).orElseThrow()
            ));
            movie5.getBookingTimes().addAll(List.of(
                    this.bookingTimeRepository.findById(9L).orElseThrow(),
                    this.bookingTimeRepository.findById(10L).orElseThrow()
            ));

            movie1.getGenreCategories().addAll(List.of(
                    this.categoryRepository.findById(2L).orElseThrow(),
                    this.categoryRepository.findById(3L).orElseThrow()
            ));
            movie2.getGenreCategories().addAll(List.of(
                    this.categoryRepository.findById(1L).orElseThrow(),
                    this.categoryRepository.findById(5L).orElseThrow()
            ));
            movie3.getGenreCategories().addAll(List.of(
                    this.categoryRepository.findById(7L).orElseThrow(),
                    this.categoryRepository.findById(11L).orElseThrow()
            ));
            movie4.getGenreCategories().addAll(List.of(
                    this.categoryRepository.findById(3L).orElseThrow(),
                    this.categoryRepository.findById(5L).orElseThrow(),
                    this.categoryRepository.findById(6L).orElseThrow()
            ));
            movie5.getGenreCategories().addAll(List.of(
                    this.categoryRepository.findById(1L).orElseThrow(),
                    this.categoryRepository.findById(9L).orElseThrow(),
                    this.categoryRepository.findById(12L).orElseThrow()
            ));

            this.movieRepository.saveAll(List.of(movie4, movie5, movie1, movie2, movie3));
        }
    }
}

package bg.softuni.CinemaTickets_Movies.models.entities;


import bg.softuni.CinemaTickets_Movies.models.enums.HallNumber;
import bg.softuni.CinemaTickets_Movies.models.enums.ProjectionFormat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "movies")
public class Movie{
    private static final String REQUIRED_URL_PART = "https://www.youtube.com/embed/";
    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(
        "(?:youtube\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "movie_class_id")
    private MovieClass movieClass;
    @Column(name = "projection_format")
    @Enumerated(EnumType.STRING)
    private ProjectionFormat projectionFormat;
    @Column(name = "movie_length", nullable = false)
    private Integer movieLength;

    @Column(name = "hall_number", nullable = false)
    @Enumerated(EnumType.STRING)
    private HallNumber hallNumber;
    @Column
    private String audio;
    @Column
    private String subtitles;
    @Column(nullable = false)
    private String description;
    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @ManyToMany
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> genreCategories;
    @ManyToMany
    @JoinTable(
            name = "movies__booking_times",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_time_id"))
    private List<BookingTime> bookingTimes;

    public Movie() {
        this.genreCategories = new ArrayList<>();
        this.bookingTimes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public Movie setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    public MovieClass getMovieClass() {
        return movieClass;
    }

    public Movie setMovieClass(MovieClass movieClass) {
        this.movieClass = movieClass;
        return this;
    }

    public ProjectionFormat getProjectionFormat() {
        return projectionFormat;
    }

    public Movie setProjectionFormat(ProjectionFormat projectionFormat) {
        this.projectionFormat = projectionFormat;
        return this;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public Movie setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public Movie setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public Movie setAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public Movie setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public Movie setTrailerUrl(String trailerUrl) {
        if (trailerUrl.startsWith(REQUIRED_URL_PART)) {
            this.trailerUrl = trailerUrl;
            return this;
        }

        Matcher matcher = YOUTUBE_PATTERN.matcher(trailerUrl);
        if (matcher.find()) {
            String videoId = matcher.group(1);
            this.trailerUrl = REQUIRED_URL_PART + videoId;
        } else {
            this.trailerUrl = trailerUrl;
        }

        return this;
    }

    public List<Category> getGenreCategories() {
        return genreCategories;
    }

    public Movie setGenreCategories(List<Category> genreCategories) {
        this.genreCategories = genreCategories;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Movie setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public List<BookingTime> getBookingTimes() {
        return bookingTimes;
    }

    public Movie setBookingTimes(List<BookingTime> bookingTimes) {
        this.bookingTimes = bookingTimes;
        return this;
    }
}

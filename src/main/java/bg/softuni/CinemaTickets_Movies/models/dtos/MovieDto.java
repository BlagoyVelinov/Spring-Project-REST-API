package bg.softuni.CinemaTickets_Movies.models.dtos;

import bg.softuni.CinemaTickets_Movies.models.entities.BookingTime;
import bg.softuni.CinemaTickets_Movies.models.entities.MovieClass;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import bg.softuni.CinemaTickets_Movies.models.enums.HallNumber;
import bg.softuni.CinemaTickets_Movies.models.enums.MovieClassEnum;
import bg.softuni.CinemaTickets_Movies.models.enums.ProjectionFormat;
import bg.softuni.CinemaTickets_Movies.validation.annotation.UniqueMovieName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class MovieDto {

    private long id;

    private String name;

    private Integer movieLength;

    private HallNumber hallNumber;

    private String audio;

    private String subtitles;

    private String description;

    private String imageUrl;

    private String trailerUrl;

    private ProjectionFormat projectionFormat;

    private MovieClass movieClass;

    private List<Genre> genreCategories;

    private List<BookingTime> bookingTimes;

    public MovieDto() {
        this.genreCategories = new ArrayList<>();
        this.bookingTimes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public MovieDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MovieDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public MovieDto setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public MovieDto setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public MovieDto setAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public MovieDto setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MovieDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public MovieDto setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public ProjectionFormat getProjectionFormat() {
        return projectionFormat;
    }

    public MovieDto setProjectionFormat(ProjectionFormat projectionFormat) {
        this.projectionFormat = projectionFormat;
        return this;
    }

    public MovieClass getMovieClass() {
        return movieClass;
    }

    public MovieDto setMovieClass(MovieClass movieClass) {
        this.movieClass = movieClass;
        return this;
    }

    public List<Genre> getGenreCategories() {
        return genreCategories;
    }

    public MovieDto setGenreCategories(List<Genre> genreCategories) {
        this.genreCategories = genreCategories;
        return this;
    }

    public List<BookingTime> getBookingTimes() {
        return bookingTimes;
    }

    public MovieDto setBookingTimes(List<BookingTime> bookingTimes) {
        this.bookingTimes = bookingTimes;
        return this;
    }
}

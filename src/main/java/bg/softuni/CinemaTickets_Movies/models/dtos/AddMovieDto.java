package bg.softuni.CinemaTickets_Movies.models.dtos;

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

public class AddMovieDto {
    @NotEmpty(message = "Name cannot be null ot empty!")
    @Size(min= 3, max = 100, message = "Name must be between 3 and 100 characters")
    @UniqueMovieName(message = "Movie with this name already exist!")
    private String name;
    @NotNull(message = "Insert a movie length")
    @Positive(message = "Movie Length should be positive number! Min-20 max-180")
    private Integer movieLength;

    @NotNull(message = "You must select hall number!")
    private HallNumber hallNumber;
    @NotEmpty
    @Size(min = 2, max = 20,
            message = "Audio length must be between 2 and 20 characters")
    private String audio;
    @NotEmpty
    @Size(min = 2, max = 20,
            message = "Subtitles length must be between 2 and 20 characters")
    private String subtitles;
    @NotEmpty(message = "Description cannot be null ot empty!")
    @Size(min = 5, max = 250,
            message = "Description must be at between 5 and 250 characters!")
    private String description;
    @NotEmpty
    @Size(min = 10, max = 250,
            message = "Put correct imageUrl / between 10 and 250 characters!")
    private String imageUrl;
    @NotEmpty
    @Size(min = 10, max = 100,
            message = "Put correct trailerUrl / between 10 and 100 characters!")
    private String trailerUrl;
    @NotNull(message = "Please select a format of projection!")
    private ProjectionFormat projectionFormat;
    @NotNull(message = "Please select a class of the movie!")
    private MovieClassEnum movieClass;
    @NotEmpty(message = "Please select min 1 category!")
    private List<Genre> genreCategories;

    public AddMovieDto() {
        this.genreCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public AddMovieDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public AddMovieDto setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public AddMovieDto setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public AddMovieDto setAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public AddMovieDto setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddMovieDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AddMovieDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public AddMovieDto setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public ProjectionFormat getProjectionFormat() {
        return projectionFormat;
    }

    public AddMovieDto setProjectionFormat(ProjectionFormat projectionFormat) {
        this.projectionFormat = projectionFormat;
        return this;
    }

    public MovieClassEnum getMovieClass() {
        return movieClass;
    }

    public AddMovieDto setMovieClass(MovieClassEnum movieClass) {
        this.movieClass = movieClass;
        return this;
    }

    public List<Genre> getGenreCategories() {
        return genreCategories;
    }

    public AddMovieDto setGenreCategories(List<Genre> genreCategories) {
        this.genreCategories = genreCategories;
        return this;
    }

}

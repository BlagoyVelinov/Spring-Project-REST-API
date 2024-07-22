package bg.softuni.CinemaTickets_Movies.services;

import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoriesByGenre(List<Genre> genreCategories);
}

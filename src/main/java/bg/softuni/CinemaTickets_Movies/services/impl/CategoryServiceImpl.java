package bg.softuni.CinemaTickets_Movies.services.impl;

import bg.softuni.CinemaTickets_Movies.models.entities.Category;
import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import bg.softuni.CinemaTickets_Movies.repositories.CategoryRepository;
import bg.softuni.CinemaTickets_Movies.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategoriesByGenre(List<Genre> genreCategories) {
        return this.categoryRepository.findAllByNameIn(genreCategories);
    }
}

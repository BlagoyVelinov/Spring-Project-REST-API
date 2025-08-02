package bg.softuni.CinemaTickets_Movies.models.entities;

import bg.softuni.CinemaTickets_Movies.models.enums.Genre;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Genre name;

    public Category() {
    }

    public Category(Genre name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public Genre getName() {
        return name;
    }

    public Category setName(Genre name) {
        this.name = name;
        return this;
    }
}

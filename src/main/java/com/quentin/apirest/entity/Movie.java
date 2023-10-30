package com.quentin.apirest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="movie")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String movieDescription;
    private int publicationYear;

    @ManyToMany
    @JoinTable(name = "actor_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actor = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "author_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> author = new HashSet<>();


}
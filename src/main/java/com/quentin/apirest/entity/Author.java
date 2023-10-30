package com.quentin.apirest.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="author")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AuthorLastName;
    private String AuthorName;
    private String BirthDate;

    @ManyToMany(mappedBy = "author")
    private Set<Movie> movies = new HashSet<>();


}
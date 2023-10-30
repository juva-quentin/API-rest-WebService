package com.quentin.apirest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="actor")
@Data
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "actor_last_name")
    private String actorLastName;
    @Column(name = "actor_name")
    private String actorName;
    private String BirthDate;

    @ManyToMany(mappedBy = "actor")
    private Set<Movie> movies = new HashSet<>();



}
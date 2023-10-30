package com.quentin.apirest.repository.movie;

import com.quentin.apirest.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m " +
            "JOIN m.actor a " +
            "JOIN m.author au " +
            "WHERE " +
            "LOWER(a.actorName) IN :actorNames " +
            "AND " +
            "LOWER(au.AuthorName) IN :authorNames ")
    Page<Movie> findByActorAndAuthorNames(List<String> actorNames, List<String> authorNames, Pageable pageable);

}

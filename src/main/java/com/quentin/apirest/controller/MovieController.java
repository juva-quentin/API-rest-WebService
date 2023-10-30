package com.quentin.apirest.controller;

import com.quentin.apirest.entity.Movie;
import com.quentin.apirest.entity.MovieDetailsDTO;
import com.quentin.apirest.entity.MovieRequest;
import com.quentin.apirest.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "Get all movies", description = "Get a list of all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Movies not found")
    })
    @GetMapping
    public ResponseEntity<Page<Movie>> getAllMovies(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20") @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.getAllMovies(pageable);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Operation(summary = "Get a movie by ID", description = "Get a movie by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailsDTO> getMovieById(@Parameter(description = "Movie ID", example = "1") @PathVariable Long id) {
        MovieDetailsDTO movie = movieService.getMovieById(id);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Filter movies by actor and author names", description = "Filter movies based on actor and author names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Movies not found")
    })
    @GetMapping("/filter")
    public ResponseEntity<Page<Movie>> getMoviesByActorAndAuthorNames(
            @Parameter(description = "Actor names to filter", in = ParameterIn.QUERY, example = "Tom Cruise") @RequestParam(value = "actorNames", required = false) List<String> actorNames,
            @Parameter(description = "Author names to filter", in = ParameterIn.QUERY, example = "John Smith") @RequestParam(value = "authorNames", required = false) List<String> authorNames,
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20") @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (actorNames == null) {
            actorNames = new ArrayList<>();
        }

        if (authorNames == null) {
            authorNames = new ArrayList<>();
        }

        Page<Movie> movies = movieService.getMoviesByActorAndAuthorNames(actorNames, authorNames, pageable);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Operation(summary = "Create a new movie", description = "Create a new movie with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<MovieDetailsDTO> createMovie(@RequestBody MovieRequest movieRequest) {
        MovieDetailsDTO createdMovie = movieService.saveMovie(movieRequest);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a movie by ID", description = "Update a movie with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieDetailsDTO> updateMovie(@Parameter(description = "Movie ID", example = "1") @PathVariable Long id, @RequestBody MovieRequest updatedMovie) {
        MovieDetailsDTO movie = movieService.updateMovie(id, updatedMovie);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a movie by ID", description = "Delete a movie by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMovie(@Parameter(description = "Movie ID", example = "1") @PathVariable Long id) {
        if (movieService.getMovieById(id) != null) {
            movieService.deleteMovie(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}

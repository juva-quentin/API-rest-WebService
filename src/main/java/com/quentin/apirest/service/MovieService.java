package com.quentin.apirest.service;

import com.quentin.apirest.entity.*;
import com.quentin.apirest.repository.actor.ActorRepository;
import com.quentin.apirest.repository.author.AuthorRepository;
import com.quentin.apirest.repository.movie.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    private final ActorRepository actorRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, AuthorRepository authorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.authorRepository = authorRepository;
    }
    @Transactional
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Transactional
    public Page<Movie> getMoviesByActorAndAuthorNames(List<String> actorNames, List<String> authorNames, Pageable pageable) {
        return movieRepository.findByActorAndAuthorNames(actorNames, authorNames, pageable);
    }

    public MovieDetailsDTO movieToDto(Movie movie){
        if (movie != null) {
            MovieDetailsDTO movieDetails = new MovieDetailsDTO();
            movieDetails.setTitle(movie.getTitle());
            movieDetails.setMovieDescription(movie.getMovieDescription());
            movieDetails.setPublicationYear(movie.getPublicationYear());

            List<String> actorNames = movie.getActor().stream()
                    .map(Actor::getActorName)
                    .collect(Collectors.toList());
            movieDetails.setActorNames(actorNames);

            List<String> authorNames = movie.getAuthor().stream()
                    .map(Author::getAuthorName)
                    .collect(Collectors.toList());
            movieDetails.setAuthorNames(authorNames);

            return movieDetails;
        }
        return null;
    }

    @Transactional
    public MovieDetailsDTO getMovieById(Long id) {

        Movie movie =  movieRepository.findById(id).orElse(null);
       return movieToDto(movie);
    }

    @Transactional
    public MovieDetailsDTO updateMovie(Long id, MovieRequest updatedMovie) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie != null) {
            // Mettez à jour les champs du film existant avec les nouvelles valeurs
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setMovieDescription(updatedMovie.getMovieDescription());
            existingMovie.setPublicationYear(updatedMovie.getPublicationYear());

            // Mettez à jour la liste des auteurs
            existingMovie.getAuthor().clear();
            existingMovie.getAuthor().addAll(authorRepository.findAllById(updatedMovie.getAuthorIds()));

            // Mettez à jour la liste des acteurs
            existingMovie.getActor().clear();
            existingMovie.getActor().addAll(actorRepository.findAllById(updatedMovie.getActorIds()));
            Movie movie = movieRepository.save(existingMovie);
            return movieToDto(movie);
        }
        return null;
    }

    @Transactional
    public MovieDetailsDTO saveMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setMovieDescription(movieRequest.getMovieDescription());
        movie.setPublicationYear(movieRequest.getPublicationYear());

        List<Author> authors = authorRepository.findAllById(movieRequest.getAuthorIds());
        List<Actor> actors = actorRepository.findAllById(movieRequest.getActorIds());

        movie.getAuthor().addAll(authors);
        movie.getActor().addAll(actors);

        Movie newMovie = movieRepository.save(movie);
        return movieToDto(newMovie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}
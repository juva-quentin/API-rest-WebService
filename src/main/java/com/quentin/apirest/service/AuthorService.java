package com.quentin.apirest.service;

import com.quentin.apirest.entity.Actor;
import com.quentin.apirest.entity.Author;
import com.quentin.apirest.entity.AuthorRequest;
import com.quentin.apirest.entity.Movie;
import com.quentin.apirest.repository.actor.ActorRepository;
import com.quentin.apirest.repository.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setAuthorName(authorRequest.getAuthorName());
        author.setAuthorLastName(authorRequest.getAuthorLastName());
        author.setBirthDate(authorRequest.getBirthDate());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, AuthorRequest updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor != null) {
            existingAuthor.setAuthorName(updatedAuthor.getAuthorName());
            existingAuthor.setAuthorLastName(updatedAuthor.getAuthorLastName());
            existingAuthor.setBirthDate(updatedAuthor.getBirthDate());
            return authorRepository.save(existingAuthor);
        }
        return null;
    }


}
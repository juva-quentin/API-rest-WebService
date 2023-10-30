package com.quentin.apirest.controller;

import com.quentin.apirest.entity.Author;
import com.quentin.apirest.entity.AuthorRequest;
import com.quentin.apirest.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/authors", produces = "application/json")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Get all authors", description = "Get a list of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Authors not found")
    })
    @GetMapping
    public ResponseEntity<Page<Author>> getAllAuthors(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20") @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorService.getAllAuthors(pageable);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Get an author by ID", description = "Get an author by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@Parameter(description = "Author ID", example = "1") @PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new author", description = "Create a new author with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorRequest author) {
        Author createdAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an author by ID", description = "Update an author with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable Long id, @Valid @RequestBody AuthorRequest updatedAuthor) {
        Author author = authorService.updateAuthor(id, updatedAuthor);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an author by ID", description = "Delete an author by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable Long id) {
        if (authorService.getAuthorById(id) != null) {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}
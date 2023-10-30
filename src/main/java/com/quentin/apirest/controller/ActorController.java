package com.quentin.apirest.controller;

import com.quentin.apirest.entity.Actor;
import com.quentin.apirest.entity.ActorRequest;
import com.quentin.apirest.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @Operation(summary = "Get all actors", description = "Get a list of all actors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actors retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Actors not found")
    })
    @GetMapping
    public ResponseEntity<Page<Actor>> getAllActors(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20") @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actors = actorService.getAllActors(pageable);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @Operation(summary = "Get an actor by ID", description = "Get an actor by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@Parameter(description = "Actor ID", example = "1") @PathVariable Long id) {
        Actor actor = actorService.getActorById(id);
        if (actor != null) {
            return new ResponseEntity<>(actor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new actor", description = "Create a new actor with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actor created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Actor> createActor(@Valid @RequestBody ActorRequest actor) {
        Actor createdActor = actorService.saveActor(actor);
        return new ResponseEntity<>(createdActor, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an actor by ID", description = "Update an actor with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@Parameter(description = "Actor ID", example = "1") @PathVariable Long id, @Valid @RequestBody ActorRequest updatedActor) {
        Actor actor = actorService.updateActor(id, updatedActor);
        if (actor != null) {
            return new ResponseEntity<>(actor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an actor by ID", description = "Delete an actor by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteActor(@Parameter(description = "Actor ID", example = "1") @PathVariable Long id) {
        if (actorService.getActorById(id) != null) {
            actorService.deleteActor(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}
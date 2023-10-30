package com.quentin.apirest.entity;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetailsDTO {
    private String title;
    private String movieDescription;
    private int publicationYear;
    private List<String> actorNames;
    private List<String> authorNames;

}
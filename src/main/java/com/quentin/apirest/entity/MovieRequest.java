package com.quentin.apirest.entity;

import lombok.Data;

import java.util.List;

@Data
public class MovieRequest {
    private String title;
    private String movieDescription;
    private int publicationYear;
    private List<Long> actorIds;
    private List<Long> authorIds;

}
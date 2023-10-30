package com.quentin.apirest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ActorRequest {
    private String actorLastName;
    private String actorName;
    private String BirthDate;
}


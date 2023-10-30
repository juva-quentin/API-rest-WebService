package com.quentin.apirest.entity;

import lombok.Data;

@Data
public class AuthorRequest {
    private String AuthorLastName;
    private String AuthorName;
    private String BirthDate;
}

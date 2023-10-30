package com.quentin.apirest.repository.author;

import com.quentin.apirest.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
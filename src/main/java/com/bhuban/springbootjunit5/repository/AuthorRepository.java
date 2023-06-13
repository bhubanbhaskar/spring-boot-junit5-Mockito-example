package com.bhuban.springbootjunit5.repository;

import com.bhuban.springbootjunit5.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

package com.bhuban.springbootjunit5.repository;

import com.bhuban.springbootjunit5.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    long deleteByTitle(String title);

}

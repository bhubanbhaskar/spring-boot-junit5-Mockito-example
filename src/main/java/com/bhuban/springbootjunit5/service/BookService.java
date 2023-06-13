package com.bhuban.springbootjunit5.service;

import com.bhuban.springbootjunit5.dto.BookDto;
import com.bhuban.springbootjunit5.exception.DuplicatedEntityException;
import com.bhuban.springbootjunit5.exception.EntityNotFoundException;
import com.bhuban.springbootjunit5.model.Book;
import com.bhuban.springbootjunit5.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Long create(BookDto bookDto) {
        if (bookDto.getId() != null) {
            bookRepository.findById(bookDto.getId()).ifPresent(book -> {
                throw new DuplicatedEntityException("Entity Book with id " + book.getId() + " already exists");

            });
        }
        Book book = modelMapper.map(bookDto, Book.class);
        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public BookDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return toDto(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private BookDto toDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
}

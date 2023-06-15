package com.bhuban.springbootjunit5.dto;

import com.bhuban.springbootjunit5.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Book Mapper Unit Tests")
public class BookDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("When convert Book entity to Book dto, then correct")
    public void WhenConvertBookEntityToBookDto_thenCorrect() {

        Book book = Book.builder().id(1L).title("Anaconda")
                .genre("comedy").build();

        BookDto bookDto = modelMapper.map(book, BookDto.class);

        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getGenre(), book.getGenre());
    }

    @Test
    @DisplayName("When convert Book dto to Book entity, then correct")
    public void whenConvertBookDtoToBookEntity_thenCorrect() {
        BookDto bookDto = BookDto.builder().id(1L).title("Anaconda").genre("Comedy")
                .build();
        Book book = modelMapper.map(bookDto, Book.class);

        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getGenre(), bookDto.getGenre());
    }
}
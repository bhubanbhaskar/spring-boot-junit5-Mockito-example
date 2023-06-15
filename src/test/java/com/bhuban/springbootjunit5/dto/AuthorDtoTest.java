package com.bhuban.springbootjunit5.dto;

import com.bhuban.springbootjunit5.model.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Author Mapper Unit Tests")
class AuthorDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("when convert Author entity to `Author dto, then correct")
    public void whenConvertAuthorEntityToAuthorDto_thenCorrect() {

        Author author = Author.builder().id(1L).email("test@Example.com")
                .firstName("bhuban").lastName("bhaskar").build();
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);

        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getFirstName(), author.getFirstName());
        assertEquals(authorDto.getLastName(), author.getLastName());
        assertEquals(authorDto.getEmail(), author.getEmail());
    }

    @Test
    @DisplayName("when convert Author dto to Author entity, then correct")
    public void WhenConvertAuthorDtoToAuthorEntity_thenCorrect() {
        AuthorDto authorDto = AuthorDto.builder().id(1L).email("test@example.com")
                .firstName("bhuban").lastName("bhaskar").build();
        Author author = modelMapper.map(authorDto, Author.class);

        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getFirstName(), authorDto.getFirstName());
        assertEquals(author.getLastName(), authorDto.getLastName());
        assertEquals(author.getEmail(), authorDto.getEmail());
    }

}
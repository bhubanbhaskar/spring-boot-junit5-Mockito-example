package com.bhuban.springbootjunit5.repository;

import com.bhuban.springbootjunit5.model.Author;
import com.bhuban.springbootjunit5.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DisplayName("Book Repository Integration Tests")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Book book1;
    private Book book2;
    private long initialCount;

    @BeforeEach
    public void init() {
        Author author = authorRepository.findById(0L).orElseGet(
                () -> {
                    Author authorsave = Author.builder().id(1L).
                            email("test@example.com").firstName("bhuban").
                            lastName("bhaskar").build();
                    return authorRepository.save(authorsave);
                });
        book1 = Book.builder().id(1L).title("The complete reference").
                description("Complete java book").
                genre("Programming").price(BigDecimal.TEN).author(author)
                .build();
        book2 = Book.builder().id(2L).title("Python Programming").
                description("To Learn Python").
                genre("Programming").price(BigDecimal.TEN).author(author)
                .build();
        initialCount = bookRepository.count();
        bookRepository.saveAll(Arrays.asList(book1, book2));
    }

    @AfterEach
    public void teardown() {
        bookRepository.deleteAll(Arrays.asList(book1, book2));
    }

    @Test
    @DisplayName("when deleteById from repository, then deleting should be successful")
    public void whenDeleteByIdFromRepository_thenDeletingShouldBeSuccessful() {
        System.out.println( "count before delete" + bookRepository.count());
        bookRepository.deleteById(1L);
        System.out.println("count after delete" + bookRepository.count());
        assertEquals(initialCount - 1, bookRepository.count());
    }

    @Test
    @Transactional
    @DisplayName("when delete from derived query, then deleting should be successful")
    public void whenDeleteFromDerivedQuery_thenDeletingShouldBeSuccessful() {
        long deleteRecords = bookRepository.deleteByTitle("Python Programming");
        assertEquals(1, deleteRecords);
        assertEquals(initialCount + 1, bookRepository.count());
    }

    @Test
    @DisplayName("when deleteAll from repository, then repository should be restored")
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeRestored() {
        bookRepository.deleteAll(Arrays.asList(book1, book2));
        assertEquals(initialCount, bookRepository.count());
    }
}
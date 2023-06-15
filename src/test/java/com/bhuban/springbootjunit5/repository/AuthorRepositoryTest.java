package com.bhuban.springbootjunit5.repository;

import com.bhuban.springbootjunit5.model.Author;
import com.bhuban.springbootjunit5.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegerationTest")
@DisplayName("Delete in Relationships Integration Tests")
public class AuthorRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;
    private long authorInitialCount;

    private Book book1;
    private Book book2;
    private long bookInitialCount;
  @BeforeEach
public void init(){
    authorInitialCount = authorRepository.count();
      bookInitialCount = bookRepository.count();
    Author authorSave1 = Author.builder().id(1L).email("test1@example.com").firstName("bhuban").lastName("bhaskar").build();
     author1 = authorRepository.save(authorSave1);

      Author authorSave2 = Author.builder().id(1L).email("test2@example.com").firstName("ravi").lastName("mohan").build();
      author2 = authorRepository.save(authorSave2);

      book1 = Book.builder().id(1L).description("The complete Reference").title("Java Programming").
              genre("java").price(BigDecimal.TEN).author(author1).build();
      book2 = Book.builder().id(2L).description("The python World").title("Python Programming").
              genre("python").price(BigDecimal.TEN).author(author2).build();

      bookRepository.saveAll(Arrays.asList(book1, book2));
  }

  @AfterEach
    public void teardown(){
      bookRepository.deleteAll(Arrays.asList(book1, book2));
      authorRepository.deleteAll(Arrays.asList(author1, author2));
  }

  @Test
    @DisplayName("when deleting Authors, then Books should be deleted too")
    public void whenDeletingAuthors_thenBooksShouldAlsoBeDeleted(){
      authorRepository.delete(author1);
      authorRepository.delete(author2);
      assertEquals(bookInitialCount, bookRepository.count());
      assertEquals(authorInitialCount, authorRepository.count());
  }
    @Test
    @DisplayName("when deleting Books, then Author should not be deleted")
    public void whenDeletingBooks_thenAuthorShouldNotBeDeleted(){

      long authorCount = authorRepository.count();
      bookRepository.deleteAll(Arrays.asList(book1, book2));

      assertEquals(bookInitialCount, bookRepository.count());
      assertEquals(authorCount, authorRepository.count());
    }
}
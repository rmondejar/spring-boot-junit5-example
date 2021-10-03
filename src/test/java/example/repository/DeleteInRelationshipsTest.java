package example.repository;

import example.model.Book;
import example.model.Author;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DisplayName("Delete in Relationships Integration Tests")
public class DeleteInRelationshipsTest {

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
    public void init() {

        authorInitialCount = authorRepository.count();

        Author mb1 = Author.builder().email("test1@example.com").firstName("Test1").lastName("Surname1").build();
        author1 = authorRepository.save(mb1);

        Author mb2 = Author.builder().email("test2@example.com").firstName("Test2").lastName("Surname2").build();
        author2 = authorRepository.save(mb2);

        book1 = Book.builder().description("example1").title("test1").genre("genre1").price(BigDecimal.TEN).author(author1).build();
        book2 = Book.builder().description("example2").title("test2").genre("genre2").price(BigDecimal.TEN).author(author2).build();

        bookInitialCount = bookRepository.count();
        bookRepository.saveAll(Arrays.asList(book1, book2));
    }

    @AfterEach
    public void teardown() {
        bookRepository.deleteAll(Arrays.asList(book1, book2));
        authorRepository.deleteAll(Arrays.asList(author1, author2));
    }

    @Test
    @DisplayName("when deleting Authors, then Books should be deleted too")
    public void whenDeletingAuthors_thenBooksShouldAlsoBeDeleted() {

        authorRepository.delete(author1);
        authorRepository.delete(author2);

        assertEquals(bookInitialCount, bookRepository.count());
        assertEquals(authorInitialCount, authorRepository.count());
    }

    @Test
    @DisplayName("when deleting Books, then Author should not be deleted")
    public void whenDeletingBooks_thenAuthorShouldNotBeDeleted() {

        //given
        long authorCount = authorRepository.count();
        bookRepository.deleteAll(Arrays.asList(book1, book2));

        assertEquals(bookInitialCount, bookRepository.count());
        assertEquals(authorCount, authorRepository.count());
    }
}

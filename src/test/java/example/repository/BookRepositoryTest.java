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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                    Author authorAux = Author.builder().email("test@example.com").firstName("Test").lastName("Surname").build();
                    return authorRepository.save(authorAux);
                });

        book1 = Book.builder().title("title1").description("bla bla").genre("Drama").price(BigDecimal.TEN).author(author).build();
        book2 = Book.builder().title("title2").description("bla bla bla").genre("Comedy").price(BigDecimal.TEN).author(author).build();

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
        bookRepository.deleteById(book1.getId());

        assertEquals(initialCount+1, bookRepository.count());
    }

    @Test
    @Transactional
    @DisplayName("when delete from derived query, then deleting should be successful")
    public void whenDeleteFromDerivedQuery_thenDeletingShouldBeSuccessful() {
        long deletedRecords = bookRepository.deleteByTitle("title1");

        assertEquals(1, deletedRecords);
        assertEquals(initialCount+1, bookRepository.count());
    }

    @Test
    @DisplayName("when deleteAll  from repository, then repository should be restored")
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeRestored() {
        bookRepository.deleteAll(Arrays.asList(book1, book2));

        assertEquals(initialCount, bookRepository.count());
    }
}

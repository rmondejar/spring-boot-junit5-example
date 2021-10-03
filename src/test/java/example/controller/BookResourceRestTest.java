package example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

import example.Application;
import example.dto.BookDto;
import example.dto.AuthorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
@DisplayName("Book Resource REST API Tests")
@Tag("IntegrationTest")
public class BookResourceRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("when GET Book list, then returns 200")
    public void whenGetBookList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/v1/books", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    @DisplayName("given Book id, when GET existing Book, then returns 200")
    public void givenBookId_whenGetNonExistingBook_thenReturns200() {

        //given
        Long book1Id = 1L;

        //when
        ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity("/api/v1/books/"+book1Id, BookDto.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getTitle());
        assertNotNull(responseEntity.getBody().getGenre());
    }

    @Test
    @DisplayName("given Book id, when GET non existing Book, then returns 404")
    public void givenNonExistentBookId_whenGetNonExistingBook_thenReturns404() {

        //given
        Long book1Id = 404L;

        //when
        ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity("/api/v1/books/"+book1Id, BookDto.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("when POST a new Book, then returns 201")
    public void givenNewBook_whenPostBook_thenReturns201() {

        //given
        AuthorDto defaultAuthor = AuthorDto.builder().id(1L).build();
        HttpEntity<BookDto> request = new HttpEntity<>(BookDto.builder().description("example").genre("Romance").title("title1").price(BigDecimal.TEN).author(defaultAuthor).build());

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/v1/books", request, Void.class);

        //then
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }

    @Test
    @DisplayName("when POST an existing Book id, then returns 422")
    public void givenExistingBookId_whenPostBook_thenReturns422() {

        //given
        Long existingBookId = 1L;
        AuthorDto defaultAuthor = AuthorDto.builder().id(1L).build();
        HttpEntity<BookDto> request = new HttpEntity<>(BookDto.builder().id(existingBookId).description("example").genre("Romance").title("title1").price(BigDecimal.TEN).author(defaultAuthor).build());

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/v1/books", request, Void.class);

        //then
        assertEquals(UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }


}

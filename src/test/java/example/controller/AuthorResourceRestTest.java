package example.controller;

import example.Application;
import example.dto.AuthorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
@DisplayName("Author Resource REST API Tests")
@Tag("IntegrationTest")
public class AuthorResourceRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("when POST a new Author, then returns 201")
    public void givenNewAuthor_whenPostAuthor_thenReturns201() {

        //given
        HttpEntity<AuthorDto> request = new HttpEntity<>(AuthorDto.builder().email("test@example.com").firstName("Test").lastName("Surname").build());

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/v1/authors", request, Void.class);

        //then
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }

    @Test
    @DisplayName("when POST an existing Author id, then returns 422")
    public void givenExistingAuthorId_whenPostAuthor_thenReturns422() {

        //given
        Long existingAuthorId = 1L;
        HttpEntity<AuthorDto> request = new HttpEntity<>(AuthorDto.builder().id(existingAuthorId).email("test@example.com").firstName("Test").lastName("Surname").build());

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/v1/authors", request, Void.class);

        //then
        assertEquals(UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("when DELETE a new Author, then returns 204")
    public void givenExistingAuthorId_whenDeleteAuthor_thenReturns204() {

        //given
        Long existingAuthorId = 2L;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //when
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/v1/authors/" + existingAuthorId, DELETE, entity, String.class);

        //then
        assertEquals(NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("when DELETE an existing Author id, then returns 404")
    public void givenNonExistingAuthorId_whenPostAuthor_thenReturns422() {

        //given
        Long existingAuthorId = 404L;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //when
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/v1/authors/" + existingAuthorId, DELETE, entity, String.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }


}

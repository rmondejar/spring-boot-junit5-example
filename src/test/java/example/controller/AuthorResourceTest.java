package example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dto.AuthorDto;
import example.exception.DuplicatedEntityException;
import example.exception.EntityNotFoundException;
import example.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DisplayName("Author Resource Integration Tests")
public class AuthorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("create Author, should return expected 201")
    public void createAuthorShouldReturn201() throws Exception {

        //given
        AuthorDto authorDto = AuthorDto.builder().email("test@example.com").firstName("Test").lastName("Surname").build();
        String json = objectMapper.writeValueAsString(authorDto);
        when(this.authorService.create(authorDto)).thenReturn(0L);

        //when-then
        this.mockMvc.perform(post("/api/v1/authors/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("create incomplete Book, should return 400")
    public void createIncompleteBookShouldReturn400() throws Exception {

        //given
        AuthorDto authorDto = AuthorDto.builder().email("test_examplecom").lastName("S@#¢∞name").build();
        String json = objectMapper.writeValueAsString(authorDto);

        //when-then
        this.mockMvc.perform(post("/api/v1/authors/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("A valid mail address is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("First name is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("An alphanumeric last name is mandatory")));
    }

    @Test
    @DisplayName("create existing Author id, should return 422")
    public void createExistingAuthorIdShouldReturn404() throws Exception {

        //given
        long existingAuthorId = 0L;
        AuthorDto author1 = AuthorDto.builder().id(existingAuthorId).email("test@example.com").firstName("Test").lastName("Surname").build();
        String json = objectMapper.writeValueAsString(author1);
        when(this.authorService.create(author1)).thenThrow(new DuplicatedEntityException());

        //when-then
        this.mockMvc.perform(post("/api/v1/authors/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("delete Author, should return expected Author Id")
    public void deleteAuthorShouldReturn204() throws Exception {

        //given
        long existingAuthorId = 0L;
        when(this.authorService.delete(existingAuthorId)).thenReturn(null);

        //when-then
        this.mockMvc.perform(delete("/api/v1/authors/"+existingAuthorId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("delete non existing Author id, should return 404")
    public void deleteExistingAuthorIdShouldReturn404() throws Exception {

        //given
        long nonExistingAuthorId = 404L;
        when(this.authorService.delete(nonExistingAuthorId)).thenThrow(new EntityNotFoundException());

        //when-then
        this.mockMvc.perform(delete("/api/v1/authors/"+nonExistingAuthorId))
                .andExpect(status().isNotFound());
    }
}

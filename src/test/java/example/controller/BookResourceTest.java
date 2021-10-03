package example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dto.AuthorDto;
import example.exception.DuplicatedEntityException;
import example.exception.EntityNotFoundException;
import example.dto.BookDto;
import example.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DisplayName("Book Resource Integration Tests")
public class BookResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("get Book, should return expected Book")
    public void getBookShouldReturn200() throws Exception {

        //given
        long bookId = 0L;
        given(this.bookService.get(bookId)).willReturn(new BookDto());

        //when-then
        this.mockMvc.perform(get("/api/v1/books/"+bookId)
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get Book list, should return complete Book list")
    public void getBookListShouldReturn200() throws Exception {

        //given
        given(this.bookService.list()).willReturn(new ArrayList<>());

        //when-then
        this.mockMvc.perform(get("/api/v1/books")
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get non existing Book, should return 404")
    public void getNotExistingBookShouldReturn404() throws Exception {

        //given
        long nonExistentBookId = 404L;
        given(this.bookService.get(nonExistentBookId))
                .willThrow(new EntityNotFoundException());

        //when-then
        this.mockMvc.perform(get("/api/v1/books/"+nonExistentBookId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("create Book, should return 201")
    public void createBookShouldReturn201() throws Exception {

        //given
        AuthorDto defaultAuthor = AuthorDto.builder().id(0L).build();
        BookDto book = BookDto.builder().description("example").genre("Drama").title("test").author(defaultAuthor).build();
        String json = objectMapper.writeValueAsString(book);
        when(this.bookService.create(book)).thenReturn(0L);

        //when-then
        this.mockMvc.perform(post("/api/v1/books/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("create existing Book id, should return 422")
    public void createExistingBookIdShouldReturn422() throws Exception {

        //given
        long existingBookId = 0L;
        AuthorDto defaultAuthor = AuthorDto.builder().id(existingBookId).build();
        BookDto book1 = BookDto.builder().description("example").genre("Drama").title("test").author(defaultAuthor).build();
        String json = objectMapper.writeValueAsString(book1);
        when(this.bookService.create(book1)).thenThrow(new DuplicatedEntityException());

        //when-then
        this.mockMvc.perform(post("/api/v1/books/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("create incomplete Book, should return 400")
    public void createIncompleteBookShouldReturn400() throws Exception {

        //given
        BookDto book1 = BookDto.builder().description("example").build();
        String json = objectMapper.writeValueAsString(book1);

        //when-then
        this.mockMvc.perform(post("/api/v1/books/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("Title is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Genre is mandatory")));
    }


}

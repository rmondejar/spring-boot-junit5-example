package example.dto;

import example.model.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Author Mapper Unit Tests")
public class AuthorDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("when convert Author entity to Author dto, then correct")
    public void whenConvertAuthorEntityToAuthorDto_thenCorrect() {

        //given
        Author author = Author.builder().id(1L).email("test@example.com").firstName("Test").lastName("Surname").build();

        //when
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);

        //then
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getFirstName(), author.getFirstName());
        assertEquals(authorDto.getLastName(), author.getLastName());
        assertEquals(authorDto.getEmail(), author.getEmail());
    }

    @Test
    @DisplayName("when convert Author dto to Author entity, then correct")
    public void whenConvertAuthorDtoToAuthorEntity_thenCorrect() {

        //given
        AuthorDto authorDto = AuthorDto.builder().id(1L).email("test@example.com").firstName("Test").lastName("Surname").build();

        //when
        Author author = modelMapper.map(authorDto, Author.class);

        //then
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getFirstName(), authorDto.getFirstName());
        assertEquals(author.getLastName(), authorDto.getLastName());
        assertEquals(author.getEmail(), authorDto.getEmail());

    }
}

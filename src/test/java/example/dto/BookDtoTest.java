package example.dto;

import example.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Book Mapper Unit Tests")
public class BookDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("when convert Book entity to Book dto, then correct")
    public void whenConvertBookEntityToBookDto_thenCorrect() {

        //given
        Book book = Book.builder().id(1L).title("Example").genre("Comedy").build();

        //when
        BookDto bookDto = modelMapper.map(book, BookDto.class);

        //then
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getGenre(), bookDto.getGenre());
    }

    @Test
    @DisplayName("when convert Book dto to Book entity, then correct")
    public void whenConvertBookDtoToBookEntity_thenCorrect() {

        //given
        BookDto bookDto = BookDto.builder().id(1L).title("Example").genre("Comedy").build();

        //when
        Book book = modelMapper.map(bookDto, Book.class);

        //then
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getGenre(), book.getGenre());
    }
}

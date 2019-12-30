package example.service;

import java.util.List;
import java.util.stream.Collectors;

import example.dto.BookDto;
import example.exception.DuplicatedEntityException;
import example.exception.EntityNotFoundException;
import example.model.Book;
import example.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Long create(BookDto bookDto) {

        if (bookDto.getId()!=null) {
            bookRepository.findById(bookDto.getId()).ifPresent(book -> {
                throw new DuplicatedEntityException("Entity Book with id " + book.getId() + " alrebooky exists");
            });
        }

        Book book = modelMapper.map(bookDto, Book.class);
        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly=true)
    public BookDto get(Long bookId) {
        Book book = bookRepository.findById(bookId)
                                        .orElseThrow(EntityNotFoundException::new);
        return toDto(book);
    }

    @Transactional(readOnly=true)
    public List<BookDto> list() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BookDto toDto(Book book) {

        return modelMapper.map(book, BookDto.class);
    }
    
}

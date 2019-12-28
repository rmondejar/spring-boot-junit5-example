package example.repository;

import example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    long deleteByTitle(String title);
}


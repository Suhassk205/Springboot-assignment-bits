package com.assignment.library.repository;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindAllBooksWithAuthor() {
        Author author = new Author("Test Author", "test@test.com");
        authorRepository.save(author);

        Book book = new Book("Test Book", "ISBN-TEST", author);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllBooksWithAuthor();

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isNotNull();
        assertThat(books.get(0).getAuthor().getName()).isEqualTo("Test Author");
    }
}

package com.assignment.library.service;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    private Author author;
    private Book book;

    @BeforeEach
    public void setUp() {
        author = new Author("John Doe", "john@example.com");
        author.setId(1L);

        book = new Book("Sample Book", "ISBN-111", author);
        book.setId(1L);
    }

    @Test
    public void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        List<Author> authors = libraryService.getAllAuthors();
        assertThat(authors).hasSize(1);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllBooksWithAuthor() {
        when(bookRepository.findAllBooksWithAuthor()).thenReturn(Arrays.asList(book));
        List<Book> books = libraryService.getAllBooksWithAuthor();
        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findAllBooksWithAuthor();
    }

    @Test
    public void testSaveBook_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        libraryService.saveBook(book, 1L);

        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testSaveBook_AuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.saveBook(book, 1L);
        });

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Book updatedInfo = new Book("Updated Title", "ISBN-222", null);

        libraryService.updateBook(1L, updatedInfo, 1L);

        verify(bookRepository, times(1)).save(book);
        assertThat(book.getTitle()).isEqualTo("Updated Title");
        assertThat(book.getIsbn()).isEqualTo("ISBN-222");
    }
}

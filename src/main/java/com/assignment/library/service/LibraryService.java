package com.assignment.library.service;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> getAllBooksWithAuthor() {
        return bookRepository.findAllBooksWithAuthor();
    }

    @Transactional
    public void saveBook(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + authorId));
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Transactional
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void updateBook(Long id, Book updatedBook, Long authorId) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + authorId));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setAuthor(author);

        bookRepository.save(existingBook);
    }
}

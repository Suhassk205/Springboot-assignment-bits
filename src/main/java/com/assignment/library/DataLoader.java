package com.assignment.library;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataLoader(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.count() == 0) {
            List<Author> authors = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                authors.add(new Author("Author " + i, "author" + i + "@example.com"));
            }
            authorRepository.saveAll(authors);

            List<Book> books = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                // Assign each book to one of the authors randomly or sequentially
                Author author = authors.get((i - 1) % authors.size());
                books.add(new Book("Book Title " + i, "ISBN-100" + i, author));
            }
            bookRepository.saveAll(books);
            
            System.out.println("Sample data initialized: 10 Authors and 10 Books.");
        }
    }
}

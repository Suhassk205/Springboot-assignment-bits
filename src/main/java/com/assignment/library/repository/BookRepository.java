package com.assignment.library.repository;

import com.assignment.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Custom query method with inner join
    @Query("SELECT b FROM Book b INNER JOIN FETCH b.author")
    List<Book> findAllBooksWithAuthor();
}

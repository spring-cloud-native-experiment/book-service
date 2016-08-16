package com.example.book.repository;

import com.example.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(@Param("id") Long id);

    Optional<Book> findByName(@Param("name") String name);

    @Query("SELECT book FROM Book book INNER JOIN book.authors author WHERE author.id = :authorId")
    Stream<Book> findBooksForAuthorId(@Param("authorId") Long authorId);
}

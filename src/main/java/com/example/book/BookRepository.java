package com.example.book;

import com.example.book.Book;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RefreshScope
interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(@Param("id") Long id);

    Optional<Book> findByName(@Param("name") String name);

    @Query("SELECT book FROM Book book INNER JOIN book.authors author WHERE author.id = :authorId")
    Stream<Book> findBooksForAuthorId(@Param("authorId") Long authorId);
}

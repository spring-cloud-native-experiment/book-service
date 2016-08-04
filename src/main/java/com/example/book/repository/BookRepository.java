package com.example.book.repository;

import com.example.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findById(@Param("id") Long id);
}

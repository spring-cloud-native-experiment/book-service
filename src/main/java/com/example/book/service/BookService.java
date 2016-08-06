package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {

    private BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> findBookByBookId(Long bookId) {
        return repository.findById(bookId);
    }

    public Stream<Book> findBooksByAuthorId(Long authorId) {
        return repository.findBooksForAuthorId(authorId);
    }
}

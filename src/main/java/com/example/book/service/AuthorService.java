package com.example.book.service;

import com.example.book.domain.Author;
import com.example.book.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AuthorService {

    private AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> findAllAuthors() {
        return repository.findAll();
    }

    public Optional<Author> findAuthorByAuthorId(Long authorId) {
        return repository.findById(authorId);
    }

    public Optional<Author> findAuthorByName(String name) {
        return repository.findByName(name);
    }

    public Stream<Author> findAuthorsByBookId(Long bookId) {
        return repository.findByBookId(bookId);
    }

    public void save(Author author) {
        repository.save(author);
    }
}

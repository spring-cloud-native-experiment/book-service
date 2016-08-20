package com.example.author;

import com.example.book.Book;
import com.example.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/authors")
class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    List<Author> getAllAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping(path = "/{authorId}")
    Author getAuthorById(@PathVariable Long authorId) {
        return authorService.findAuthorByAuthorId(authorId)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @GetMapping(path = "/{authorId}/books")
    List<Book> getBooksForAuthor(@PathVariable Long authorId) {
        return authorService.findAuthorByAuthorId(authorId)
                .map(Author::getId)
                .map(bookService::findBooksByAuthorId)
                .orElseThrow(AuthorNotFoundException::new)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Author> save(@RequestBody Author author) {
        Author savedAuthor = authorService.save(author);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(MvcUriComponentsBuilder
                        .fromMethodCall(on(AuthorController.class)
                                .getAuthorById(savedAuthor.getId()))
                        .build().toUri())
                .body(savedAuthor);
    }
}

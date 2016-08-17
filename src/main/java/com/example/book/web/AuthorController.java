package com.example.book.web;

import com.example.book.domain.Author;
import com.example.book.domain.Book;
import com.example.book.exception.AuthorNotFoundException;
import com.example.book.service.AuthorService;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(method = GET)
    List<Author> getAllAuthors() {
        return authorService.findAllAuthors();
    }

    @RequestMapping(path = "/{authorId}", method = GET)
    Author getAuthorById(@PathVariable Long authorId) {
        return authorService.findAuthorByAuthorId(authorId)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @RequestMapping(path = "/{authorId}/books", method = GET)
    List<Book> getBooksForAuthor(@PathVariable Long authorId) {
        return authorService.findAuthorByAuthorId(authorId)
                .map(Author::getId)
                .map(bookService::findBooksByAuthorId)
                .orElseThrow(AuthorNotFoundException::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/author", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Author save(@RequestBody Author author) {
        return authorService.save(author);
    }
}

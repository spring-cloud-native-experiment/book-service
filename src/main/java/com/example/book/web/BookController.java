package com.example.book.web;

import com.example.book.domain.Author;
import com.example.book.domain.Book;
import com.example.book.exception.BookNotFoundException;
import com.example.book.service.AuthorService;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/books")
class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @RequestMapping(method = GET)
    List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @RequestMapping(path = "/{bookId}", method = GET)
    Book getBookById(@PathVariable Long bookId) {
        return bookService.findBookByBookId(bookId)
                .orElseThrow(BookNotFoundException::new);
    }

    @RequestMapping(path = "/{bookId}/authors", method = GET)
    List<Author> getAuthorsForBook(@PathVariable Long bookId) {
        return bookService.findBookByBookId(bookId)
                .map(Book::getId)
                .map(authorService::findAuthorsByBookId)
                .orElseThrow(BookNotFoundException::new)
                .collect(Collectors.toList());
    }
}

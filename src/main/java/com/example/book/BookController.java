package com.example.book;

import com.example.author.Author;
import com.example.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
                .collect(toList());
    }

    @RequestMapping(path = "/book", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Book save(@RequestBody Book book) {
        return bookService.save(book);
    }
}

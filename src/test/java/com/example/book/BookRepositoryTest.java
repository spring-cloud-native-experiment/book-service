package com.example.book;

import com.example.author.Author;
import com.example.book.Book;
import com.example.book.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findsBookById() throws Exception {
        String bookName = "Java 8 Streams In Action";
        Book book = Book.builder().name(bookName).build();
        Long bookId = this.testEntityManager.persistAndGetId(book, Long.class);

        Optional<Book> savedBook = bookRepository.findById(bookId);

        assertThat(savedBook.isPresent()).isTrue();
        assertThat(savedBook.get().getId()).isEqualTo(bookId);
        assertThat(savedBook.get().getName()).isEqualTo(bookName);
    }

    @Test
    public void findBooksByAuthor() throws Exception {
        String bookName = "Java 8 Streams in Action";
        Author author = Author.builder().name("Test").build();
        Book book = Book.builder().name(bookName).authors(singletonList(author)).build();
        Long bookId = this.testEntityManager.persistAndGetId(book, Long.class);
        this.testEntityManager.persist(author);

        Stream<Book> savedAuthor = bookRepository.findBooksForAuthorId(author.getId());

        long count = savedAuthor.map(auth -> {
            long id = auth.getId();
            assertThat(id).isEqualTo(bookId);
            return id;
        }).count();
        assertThat(count).isEqualTo(1);
    }
}
package com.example.author;

import com.example.author.Author;
import com.example.author.AuthorRepository;
import com.example.book.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findsAuthorById() throws Exception {
        String authorName = "Erich Gamma";
        Author author = Author.builder().name(authorName).build();
        Long authorId = this.testEntityManager.persistAndGetId(author, Long.class);

        Optional<Author> savedAuthor = authorRepository.findById(authorId);

        assertThat(savedAuthor).hasValueSatisfying(expected -> {
            assertThat(expected.getId()).isEqualTo(authorId);
            assertThat(expected.getName()).isEqualTo(authorName);
        });
    }

    @Test
    public void findAuthorByBook() throws Exception {
        String authorName = "Erich Gamma";
        Author author = Author.builder().name(authorName).build();
        Book book = Book.builder().name("Test").authors(asList(author)).build();
        this.testEntityManager.persist(book);
        Long authorId = this.testEntityManager.persistAndGetId(author, Long.class);

        Stream<Author> savedAuthor = authorRepository.findByBookId(book.getId());

        assertThat(savedAuthor).extracting(Author::getId).contains(authorId).hasSize(1);
    }
}
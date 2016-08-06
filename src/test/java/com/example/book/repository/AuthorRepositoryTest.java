package com.example.book.repository;

import com.example.book.domain.Author;
import com.example.book.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
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

        assertThat(savedAuthor.isPresent()).isTrue();
        assertThat(savedAuthor.get().getId()).isEqualTo(authorId);
        assertThat(savedAuthor.get().getName()).isEqualTo(authorName);
    }

    @Test
    public void findAuthorByBook() throws Exception {
        String authorName = "Erich Gamma";
        Book book = Book.builder().name("Test").build();
        Author author = Author.builder().name(authorName).books(asList(book)).build();
        this.testEntityManager.persist(book);
        Long authorId = this.testEntityManager.persistAndGetId(author, Long.class);

        Stream<Author> savedAuthor = authorRepository.findByBookId(book.getId());

        long count = savedAuthor.map(auth -> {
            long id = auth.getId();
            assertThat(id).isEqualTo(authorId);
            return id;
        }).count();
        assertThat(count).isEqualTo(1);
    }
}
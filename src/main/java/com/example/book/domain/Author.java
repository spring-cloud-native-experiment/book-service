package com.example.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Book_Author",
            inverseJoinColumns = @JoinColumn(name = "AuthorId"),
            joinColumns = @JoinColumn(name = "BookId")
    )
    private List<Book> books;
}

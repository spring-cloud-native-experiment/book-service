package com.example.book.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorService {

    public List<Long> getAuthorIdsForBookId(Long bookId) {
        return Collections.emptyList();
    }
}

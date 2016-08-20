package com.example.author;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such author")
class AuthorNotFoundException extends RuntimeException {
}

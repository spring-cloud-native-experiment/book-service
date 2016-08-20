package com.example.author;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Author already exists")
class AuthorAlreadyExistsException extends RuntimeException {
}

package org.example.scheduleproject.controller.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class NoRequestException extends RuntimeException {
    public NoRequestException(String message) {
        super(message);
    }
}

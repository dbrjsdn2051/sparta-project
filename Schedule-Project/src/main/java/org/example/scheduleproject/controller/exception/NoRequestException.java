package org.example.scheduleproject.controller.exception;

public class NoRequestException extends RuntimeException {
    public NoRequestException(String message) {
        super(message);
    }
}

package org.example.todolistproject.exception;

import lombok.Getter;

@Getter
public class ApiError {

    private final String message;
    private final int status;

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
    }
}

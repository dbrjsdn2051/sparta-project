package org.example.todolistproject.exception;

import jakarta.annotation.Nullable;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException() {
        super("허가된 접근이 아닙니다!!");
    }
}

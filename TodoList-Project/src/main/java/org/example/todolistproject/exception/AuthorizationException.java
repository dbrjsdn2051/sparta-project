package org.example.todolistproject.exception;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException() {
        super("허가된 접근이 아닙니다!!");
    }
}

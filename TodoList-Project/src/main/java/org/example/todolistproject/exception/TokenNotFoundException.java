package org.example.todolistproject.exception;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException() {
        super("인증 정보가 존재하지 않습니다.");
    }
}

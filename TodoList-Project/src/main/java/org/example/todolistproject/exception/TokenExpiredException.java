package org.example.todolistproject.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("인증 정보가 만료되었습니다. 다시 로그인 시도해주세요 !!");
    }
}

package org.example.todolistproject.exception;

public class MissMatchPasswordException extends RuntimeException{
    public MissMatchPasswordException() {
        super("비밀번호가 틀려요!!");
    }
}

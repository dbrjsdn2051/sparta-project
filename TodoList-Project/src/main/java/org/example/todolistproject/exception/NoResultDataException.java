package org.example.todolistproject.exception;

public class NoResultDataException extends RuntimeException{
    public NoResultDataException() {
        super("데이터가 존재하지 않아요!!");
    }
}

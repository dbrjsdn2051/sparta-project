package org.example.todolistproject.exception;

public class NoRequestDataException extends RuntimeException{
    public NoRequestDataException() {
        super("데이터가 존재하지 않아요!!");
    }
}

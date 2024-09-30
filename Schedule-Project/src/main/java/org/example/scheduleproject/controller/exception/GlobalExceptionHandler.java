package org.example.scheduleproject.controller;

import org.apache.coyote.BadRequestException;
import org.example.scheduleproject.controller.exception.NoRequestException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> missMatch(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoRequestException.class)
    public ResponseEntity<String> noResult(NoRequestException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> emptyData(EmptyResultDataAccessException ex){
        String e = "요청한 데이터를 찾을 수 없어요 !!";
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

}

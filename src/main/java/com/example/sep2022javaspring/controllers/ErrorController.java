package com.example.sep2022javaspring.controllers;

import com.example.sep2022javaspring.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDto> handleException(DataIntegrityViolationException e, WebRequest webRequest) {
        return ResponseEntity
                .status(500)
                .body(ErrorDto.builder()
                        .statusCode(500)
                        .messages(List.of(e.getRootCause().getMessage()))
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException e, WebRequest webRequest) {
        return ResponseEntity
                .status(400)
                .body(ErrorDto.builder()
                        .statusCode(400)
                        .messages(e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList())
                        .build());
    }

}
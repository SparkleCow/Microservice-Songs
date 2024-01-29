package com.cowradio.microservicesecurity.controllers;

import com.cowradio.microservicesecurity.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler(value = ResultNotFoundException.class)
    public ResponseEntity<ErrorMessage> noResultExceptionHandler(ResultNotFoundException resultNotFoundException){
        ErrorMessage errorMessage = new ErrorMessage("Error 404 Not found", resultNotFoundException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        ErrorMessage errorMessage = new ErrorMessage("Error 400 Bad request", methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateElementException.class)
    public ResponseEntity<ErrorMessage> duplicateElementExceptionHandler(DuplicateElementException duplicateElementException) {
        ErrorMessage errorMessage = new ErrorMessage("Error 400 Bad request", duplicateElementException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SaveFailureException.class)
    public ResponseEntity<ErrorMessage> saveFailureExceptionHandler(SaveFailureException saveFailureException){
        ErrorMessage errorMessage = new ErrorMessage("Error 500 Internal server error", saveFailureException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<ErrorMessage> authorizationExceptionHandler(AuthorizationException authorizationException){
        ErrorMessage errorMessage = new ErrorMessage("Error 401 Unauthorized", authorizationException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationExceptionHandler(AuthenticationException authenticationException){
        ErrorMessage errorMessage = new ErrorMessage("Error 401 Unauthorized", authenticationException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}


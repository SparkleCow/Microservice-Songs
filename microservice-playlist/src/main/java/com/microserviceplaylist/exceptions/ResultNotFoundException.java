package com.microserviceplaylist.exceptions;

public class ResultNotFoundException extends RuntimeException{
    public ResultNotFoundException(String message) {
        super(message);
    }
}

package com.cowradio.microservicegateway.exceptions;

public class ResultNotFoundException extends RuntimeException{
    public ResultNotFoundException(String message) {
        super(message);
    }
}

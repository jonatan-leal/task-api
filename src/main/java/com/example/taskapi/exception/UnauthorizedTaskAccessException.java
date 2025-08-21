package com.example.taskapi.exception;

public class UnauthorizedTaskAccessException extends RuntimeException{
    public UnauthorizedTaskAccessException(String message) {
        super(message);
    }
}

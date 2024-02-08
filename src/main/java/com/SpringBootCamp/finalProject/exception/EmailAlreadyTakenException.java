package com.SpringBootCamp.finalProject.exception;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.tripplanner.citybreak.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException(String message) {
        super(message);
    }
}

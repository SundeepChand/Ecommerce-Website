package com.example.ecommerce.exceptions;

public class AuthenticationFailedException extends IllegalArgumentException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}

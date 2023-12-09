package com.example.ecommerce.exceptions;

public class UserNotCreatedException extends IllegalArgumentException {
    public UserNotCreatedException(String message) {
        super(message);
    }
}

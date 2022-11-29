package com.example.Library.exceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String email) {
        super("User with email" + email + " already exists");
    }
}

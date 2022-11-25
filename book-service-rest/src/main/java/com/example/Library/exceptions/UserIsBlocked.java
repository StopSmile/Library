package com.example.Library.exceptions;

public class UserIsBlocked extends RuntimeException {
    public UserIsBlocked(String email) {
        super("User with email : " + email + " is blocked");
    }
}

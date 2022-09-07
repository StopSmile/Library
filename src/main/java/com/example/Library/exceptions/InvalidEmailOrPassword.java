package com.example.Library.exceptions;

public class InvalidEmailOrPassword extends RuntimeException {
    public InvalidEmailOrPassword(String email, String password){
        super("Invalid email/password combination : " + email + " " + password);
    }
}

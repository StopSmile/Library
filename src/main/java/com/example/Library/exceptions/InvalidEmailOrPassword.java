package com.example.Library.exceptions;

public class InvalidEmailOrPassword extends RuntimeException {
    public InvalidEmailOrPassword(String email, String password){
        super("You put incorrect email : " + email+ " or password " + password);

    }
}

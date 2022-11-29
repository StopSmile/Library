package com.example.Library.exceptions;

public class IncorrectRoleException extends RuntimeException{
    public IncorrectRoleException(String role) {
        super("You set incorrect user role : " + role + ". At this moment we support 3 roles: ADMIN, CLIENT and GUEST. Please choose one of these.");
    }

}

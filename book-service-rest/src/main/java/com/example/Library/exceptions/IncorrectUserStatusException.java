package com.example.Library.exceptions;

public class IncorrectUserStatusException extends RuntimeException {
    public IncorrectUserStatusException(String userStatus) {
        super("You set incorrect user status : " + userStatus + ". At this moment we support 2 statuses: ACTIVE and BANNED. Please choose one of these.");
    }
}

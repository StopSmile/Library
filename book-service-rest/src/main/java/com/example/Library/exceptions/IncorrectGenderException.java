package com.example.Library.exceptions;

public class IncorrectGenderException extends RuntimeException{
    public IncorrectGenderException(String gender) {
        super("You set incorrect user gender : " + gender + ". At this moment we support 2 genders: MALE and FEMALE. Please choose one of these.");
    }
}

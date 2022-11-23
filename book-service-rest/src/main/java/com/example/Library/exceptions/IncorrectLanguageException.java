package com.example.Library.exceptions;

public class IncorrectLanguageException extends RuntimeException {
    public IncorrectLanguageException(String language) {
        super("You set incorrect user gender : " + language + ". At this moment we support 2 languages: ENG and UA. Please choose one language.");
    }
}

package com.example.Library.exceptions;

public class BookNotFoundByTitleException extends RuntimeException {
    public BookNotFoundByTitleException(String title){
        super("Could not find book with title : " + title);
    }
}

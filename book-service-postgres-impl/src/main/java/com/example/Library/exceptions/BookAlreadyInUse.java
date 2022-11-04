package com.example.Library.exceptions;

public class BookAlreadyInUse extends RuntimeException{
    public BookAlreadyInUse(Long id){
        super("Book with id : " + id + " is already in use");
    }
}

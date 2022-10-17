package com.example.Library.exceptions;

public class BookAlreadyInLibrary extends RuntimeException{
    public BookAlreadyInLibrary(Long id){
        super("Book with id : " + id + " is already in Library");
    }
}

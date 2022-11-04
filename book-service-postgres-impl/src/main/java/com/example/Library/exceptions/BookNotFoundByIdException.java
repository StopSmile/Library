package com.example.Library.exceptions;

public class BookNotFoundByIdException extends RuntimeException {
   public BookNotFoundByIdException(Long id){
        super("Could not find book with id : " + id);
    }
}

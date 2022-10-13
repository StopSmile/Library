package com.example.Library.exceptions;

public class EmptySecretException extends RuntimeException{
    public EmptySecretException(){
        super("'secret' shouldn't be empty");
    }
}

package com.example.Library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookAndUserExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(BookNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookNotFoundHandler(BookNotFoundByIdException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookNotFoundByTitleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookAlreadyInLibraryHandler(BookNotFoundByTitleException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookAlreadyInUse.class)
    @ResponseStatus(HttpStatus.LOCKED)
    String bookAlreadyInUseHandler(BookAlreadyInUse ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookAlreadyInLibrary.class)
    @ResponseStatus(HttpStatus.LOCKED)
    String bookAlreadyInLibraryHandler(BookAlreadyInLibrary ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IncorrectLanguageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String incorrectLanguageExceptionHandler(IncorrectLanguageException ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(InvalidEmailOrPassword.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String InvalidEmailOrPassword(InvalidEmailOrPassword ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(IncorrectGenderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IncorrectGenderException(IncorrectGenderException ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(IncorrectRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IncorrectRoleException(IncorrectRoleException ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(IncorrectUserStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IncorrectUserStatusException(IncorrectUserStatusException ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UserAlreadyExists(UserAlreadyExists ex){
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(UserIsBlocked.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String UserIsBlocked(UserIsBlocked ex){
        return ex.getMessage();
    }

}

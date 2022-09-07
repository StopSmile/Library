package com.example.Library.controller;

import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.enums.BookStatus;
import com.example.Library.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Clients")
public class ClientRestController {



    private final BookService bookService;

    public ClientRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('user:client')")
    @GetMapping("/getAllBook")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookService.findAll();
    }
    @PreAuthorize("hasAuthority('user:client')")
    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }
    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/takeBook/{id}")
    public Optional<Book> takeBook(@PathVariable Long id) {
        if (bookService.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookService.findById(id).get().getBookStatus().equals(BookStatus.NOT_IN_THE_LIBRARY)) {
            throw new BookAlreadyInUse(id);
        }
        return bookService.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.NOT_IN_THE_LIBRARY);
                    return bookService.save(book);
                });
    }
    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/returnBook/{id}")
    public Optional<Book> returnTheBook(@PathVariable Long id) {
        if (bookService.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookService.findById(id).get().getBookStatus().equals(BookStatus.IN_THE_LIBRARY)) {
            throw new BookAlreadyInLibrary(id);
        }

        return bookService.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.IN_THE_LIBRARY);
                    return bookService.save(book);
                });

    }

}

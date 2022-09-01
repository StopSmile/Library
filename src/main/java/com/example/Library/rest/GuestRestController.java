package com.example.Library.rest;


import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Guests")
public class GuestRestController {


    private final BookRepository bookRepository;

    @Autowired
    public GuestRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @PreAuthorize("hasAuthority('user:guest')")
    @GetMapping("/getAllBook")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookRepository.findAll();
    }

    @PreAuthorize("hasAuthority('user:guest')")
    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookRepository.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }

}

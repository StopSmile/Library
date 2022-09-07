package com.example.Library.controller;


import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/Guests")
public class GuestRestController {


    private final BookService bookService;

    @Autowired
    public GuestRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAllBook")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookService.getAllBooks();
    }

    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }

}

package com.example.Library.rest;


import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/guests")
public class GuestRestController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/getAll")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookRepository.findAll();
    }

    @GetMapping("getByTitle")
    public Book getBook(String title){
        return bookRepository.getBookByTitle(title);
    }

}

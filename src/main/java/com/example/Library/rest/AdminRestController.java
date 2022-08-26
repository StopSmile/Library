package com.example.Library.rest;

import com.example.Library.model.Book;
import com.example.Library.model.enums.Language;
import com.example.Library.model.enums.Status;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/Admins")
public class AdminRestController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/getAllBook")
    public ArrayList<Book> getAllBook(){
        return (ArrayList<Book>) bookRepository.findAll();
    }
    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title){
        return bookRepository.getBookByTitle(title);
    }

    @PostMapping("/addBook")
    public void addBook(@RequestParam(value = "title")String title,
                         @RequestParam(value = "author")String author,
                         @RequestParam(value = "pages")int pages,
                         @RequestParam(value = "language")String language){

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPages(pages);
        book.setStatus(new Status(1,"IN_THE_LIBRARY"));
        if (language.equals("UA")){
            book.setLanguage(new Language(1,"UKRAINE"));
        }
        if (language.equals("ENG")){
            book.setLanguage(new Language(2,"ENGLISH"));
        }
        bookRepository.save(book);

    }
}

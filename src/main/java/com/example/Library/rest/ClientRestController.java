package com.example.Library.rest;

import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.model.enums.Status;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Clients")
public class ClientRestController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/getAllBook")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookRepository.findAll();
    }

    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookRepository.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }

    @PutMapping("/takeBook/{id}")
    private Optional<Book> takeBook(@PathVariable Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getStatus().getId() == 2) {
            throw new BookAlreadyInUse(id);
        }
        return bookRepository.findById(id)
                .map(book -> {
                    book.setStatus(new Status(2, "NOT_IN_THE_LIBRARY"));
                    return bookRepository.save(book);
                });
    }

    @PutMapping("/returnBook/{id}")
    private Optional<Book> returnTheBook(@PathVariable Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getStatus().getId() == 1) {
            throw new BookAlreadyInLibrary(id);
        }
        return bookRepository.findById(id)
                .map(book -> {
                    book.setStatus(new Status(1, "IN_THE_LIBRARY"));
                    return bookRepository.save(book);
                });

    }

}

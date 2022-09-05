package com.example.Library.rest;

import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.model.enums.BookStatus;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Clients")
public class ClientRestController {


    private final BookRepository bookRepository;

    @Autowired
    public ClientRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @PreAuthorize("hasAuthority('user:client')")
    @GetMapping("/getAllBook")
    public ArrayList<Book> getAll(){
        return (ArrayList<Book>) bookRepository.findAll();
    }
    @PreAuthorize("hasAuthority('user:client')")
    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookRepository.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }
    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/takeBook/{id}")
    public Optional<Book> takeBook(@PathVariable Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.NOT_IN_THE_LIBRARY)) {
            throw new BookAlreadyInUse(id);
        }
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.NOT_IN_THE_LIBRARY);
                    return bookRepository.save(book);
                });
    }
    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/returnBook/{id}")
    public Optional<Book> returnTheBook(@PathVariable Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.IN_THE_LIBRARY)) {
            throw new BookAlreadyInLibrary(id);
        }

        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.IN_THE_LIBRARY);
                    return bookRepository.save(book);
                });

    }

}

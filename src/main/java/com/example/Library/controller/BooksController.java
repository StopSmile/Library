package com.example.Library.controller;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.exceptions.IncorrectLanguageException;
import com.example.Library.model.Book;
import com.example.Library.model.Language;
import com.example.Library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('user:guest')")
    @GetMapping()
    public Page<Book> getAllBook(@RequestParam (value = "title", required = false)String title,
                                 @RequestParam (value = "page", required = false)Optional<Integer> page,
                                 @RequestParam (value = "sortBy", required = false)Optional<String> sortBy) {
        return bookService.getAllBooksWithFilterByTitle(page,sortBy,title);
    }

    @PreAuthorize("hasAuthority('user:guest')")
    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundByIdException(id));
    }

    @PreAuthorize("hasAuthority('user:admin')")
    @PostMapping()
    public Book addBook(@RequestParam(value = "title") String title,
                        @RequestParam(value = "author") String author,
                        @RequestParam(value = "pages") int pages,
                        @RequestParam(value = "language") String language) {

        if (!language.equals("UA") && !language.equals("ENG")) {
            throw new IncorrectLanguageException(language);
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPages(pages);
        book.setBookStatus(BookStatus.IN_THE_LIBRARY);

        if (language.equals("UA")) {
            book.setLanguage(new Language(1, "UKRAINE"));
        }
        if (language.equals("ENG")) {
            book.setLanguage(new Language(2, "ENGLISH"));
        }
        return bookService.addBook(book);
    }

    @PreAuthorize("hasAuthority('user:admin')")
    @PostMapping("/full")
    public Book addBook2(@RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }

    @PreAuthorize("hasAuthority('user:admin')")
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable long id) {
        if (bookService.getBookById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        bookService.deleteBookById(id);
    }

    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/takeBook/{id}")
    public Optional<Book> takeBook(@PathVariable long id) {
        return bookService.takeBook(id);
    }

    @PreAuthorize("hasAuthority('user:client')")
    @PutMapping("/returnBook/{id}")
    public Optional<Book> returnTheBook(@PathVariable long id) {
        return bookService.returnBook(id);
    }
}

package com.example.Library.controller;

import com.example.Library.exceptions.*;
import com.example.Library.model.Book;
import com.example.Library.enums.BookStatus;
import com.example.Library.model.Language;
import com.example.Library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Admins")
public class AdminRestController {
    private final BookService bookService;

    @Autowired
    public AdminRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAllBook")
    @PreAuthorize("hasAuthority('user:admin')")
    public ArrayList<Book> getAllBook() {
        return (ArrayList<Book>) bookService.getAllBooks();
    }

    @PreAuthorize("hasAuthority('user:admin')")
    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }
    @PreAuthorize("hasAuthority('user:admin')")
    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundByIdException(id));
    }

    @PreAuthorize("hasAuthority('user:admin')")
    @PostMapping("/addBook")
    public Book addBook(@RequestParam(value = "title") String title,
                        @RequestParam(value = "author") String author,
                        @RequestParam(value = "pages") int pages,
                        @RequestParam(value = "language") String language) {

        if (!language.equals("UA") && !language.equals("ENG")){
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
    @PostMapping("/addBook2")
    public Book addBook2(@RequestBody Book newBook) {

        return bookService.addBook(newBook);
    }
    @PreAuthorize("hasAuthority('user:admin')")
    @DeleteMapping("/deleteBook/{id}")
    public void deleteBookById(@PathVariable long id) {
        if (bookService.getBookById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        bookService.deleteBookById(id);
    }
    @PreAuthorize("hasAuthority('user:admin')")
    @PutMapping("/takeBook/{id}")
    public Optional<Book> takeBook(@PathVariable long id) {
        return bookService.takeBook(id);
    }
    @PreAuthorize("hasAuthority('user:admin')")
    @PutMapping("/returnBook/{id}")
    public Optional<Book> returnTheBook(@PathVariable long id) {
        return bookService.returnBook(id);
    }
}

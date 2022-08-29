package com.example.Library.rest;

import com.example.Library.exceptions.*;
import com.example.Library.model.Book;
import com.example.Library.model.enums.Language;
import com.example.Library.model.enums.Status;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Admins")
public class AdminRestController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/getAllBook")
    public ArrayList<Book> getAllBook() {
        return (ArrayList<Book>) bookRepository.findAll();
    }

    @GetMapping("/getBookByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookRepository.getBookByTitle(title)
                .orElseThrow(() -> new BookNotFoundByTitleException(title));
    }

    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundByIdException(id));
    }

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
        book.setStatus(new Status(1, "IN_THE_LIBRARY"));
        if (language.equals("UA")) {
            book.setLanguage(new Language(1, "UKRAINE"));
        }
        if (language.equals("ENG")) {
            book.setLanguage(new Language(2, "ENGLISH"));
        }
        return bookRepository.save(book);
    }

    @PostMapping("/addBook2")
    public Book addBook2(@RequestBody Book newBook) {

        return bookRepository.save(newBook);
    }

    @DeleteMapping("/deleteBook/{id}")
    public void deleteBookById(@PathVariable long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundByIdException(id);
        }
        bookRepository.deleteById(id);
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

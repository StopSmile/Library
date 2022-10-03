package com.example.Library.controller;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.IncorrectLanguageException;
import com.example.Library.model.Book;
import com.example.Library.model.Language;
import com.example.Library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
public class BooksController {

    private final static int START_PAGE_BY_DEFAULT = 0;
    private final static String SORT_BY_DEFAULT_FIELD = "id";
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('books:read')")
    @GetMapping()
    @Operation(summary = "Get all books in pages. Get book by title.")
    public Page<Book> getAllBook(@RequestParam(value = "title") Optional<String> title,
                                 @RequestParam(value = "page") Optional<Integer> page,
                                 @RequestParam(value = "sortBy") Optional<String> sortBy) {
        log.info("Executing method GET /api/v1/books with params : title = " + title + " page = " + page + " sortBy = " + sortBy);
        if (title.isPresent()) {
            return bookService.getBookByTitle(title.get());
        }
        return bookService.getAllBooksInPages(page.orElse(START_PAGE_BY_DEFAULT), sortBy.orElse(SORT_BY_DEFAULT_FIELD));
    }

    @PreAuthorize("hasAuthority('books:read')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public Book getBook(@PathVariable long id) {
        log.info("Executing method GET /api/v1/books/{id} with id " + id);
        return bookService.getBookById(id).orElseThrow(() -> new BookNotFoundByIdException(id));
    }

    @PreAuthorize("hasAuthority('books:create')")
    @PostMapping()
    @Operation(summary = "Add a book to the library using parameters.")
    public Book addBook(@RequestParam(value = "title") String title,
                        @RequestParam(value = "author") String author,
                        @RequestParam(value = "pages") int pages,
                        @RequestParam(value = "language") String language) {
        log.info("Executing method POST /api/v1/books with params : title = " + title + ",author = " + author + ",pages = " + pages + ",language = " + language);

        if (!language.equals("UA") && !language.equals("ENG")) {
            log.error("You set incorrect book language : " + language + ". At this moment we support 2 languages: ENG and UA. Please choose one language.");
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

    @PreAuthorize("hasAuthority('books:create')")
    @PostMapping("/full")
    @Operation(summary = "Add a book to the library using JSON format")
    public Book addBook2(@RequestBody Book newBook) {
        log.info("Executing method POST /api/v1/books/full in JSON format + " + newBook);
        return bookService.addBook(newBook);
    }

    @PreAuthorize("hasAuthority('books:delete')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id")
    public void deleteBookById(@PathVariable long id) {
        log.info("Executing method DELETE /api/v1/books/{id} with id + " + id);
        if (bookService.getBookById(id).isEmpty()) {
            log.error("Could not find book with id : " + id);
            throw new BookNotFoundByIdException(id);
        }
        bookService.deleteBookById(id);
    }

    @PreAuthorize("hasAuthority('books:update')")
    @PutMapping("/takeBook/{id}")
    @Operation(summary = "Take a book from the library by id")
    public Optional<Book> takeBook(@PathVariable long id) {
        log.info("Executing method PUT /api/v1/books/takeBook/{id} with id + " + id);
        return bookService.takeBook(id);
    }

    @PreAuthorize("hasAuthority('books:update')")
    @PutMapping("/returnBook/{id}")
    @Operation(summary = "Return a book to the library by id")
    public Optional<Book> returnTheBook(@PathVariable long id) {
        log.info("Executing method PUT /api/v1/books/returnBook/{id} with id + " + id);
        return bookService.returnBook(id);
    }
}

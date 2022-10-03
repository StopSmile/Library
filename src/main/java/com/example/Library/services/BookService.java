package com.example.Library.services;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
public class BookService {

    private final static Integer PAGE_SIZE = 5;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(long id) {
        log.info("Get book by id " + id);
        return bookRepository.findById(id);
    }

    public Iterable<Book> getBooksByTitle(String title) {
        log.info("Get books by title " + title);
        return bookRepository.getBooksByTitle(title);
    }

    public Iterable<Book> getAllBooks() {
        log.info("Get all books");
        return bookRepository.findAll();
    }

    public Book addBook(Book entity) {
        log.info("Add book " + entity);
        return bookRepository.save(entity);
    }

    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> takeBook(long id) {
        if (bookRepository.findById(id).isEmpty()) {
            log.error("Book not found by id " + id);
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.NOT_IN_THE_LIBRARY)) {
            log.error("Book with " + id + " already in Library");
            throw new BookAlreadyInUse(id);
        }
        log.info("Take book with id " + id);
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.NOT_IN_THE_LIBRARY);
                    return bookRepository.save(book);
                });
    }

    public Optional<Book> returnBook(long id) {
        if (bookRepository.findById(id).isEmpty()) {
            log.error("Book not found by id " + id);
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.IN_THE_LIBRARY)) {
            log.error("Book with " + id + " already in Library");
            throw new BookAlreadyInLibrary(id);
        }

        log.info("Return book with id " + id);
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.IN_THE_LIBRARY);
                    return bookRepository.save(book);
                });
    }

    public Page<Book> getAllBooksInPages(Integer page, String sortBy) {
        log.info("Get all books in pages. Start from page " + page + " and sort by " + sortBy);
        return bookRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.Direction.ASC, sortBy));
    }

    public Page<Book> getBookByTitle(String title) {
        ArrayList<Book> books = (ArrayList<Book>) bookRepository.getBooksByTitle(title);
        if (books.size() == 0) {
            log.error("Book Not Found By Title : " + title);
            throw new BookNotFoundByTitleException(title);
        } else {
            log.info("Get book by title :" + title);
            return new PageImpl<>(books);
        }
    }
}

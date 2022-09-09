package com.example.Library.services;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book entity) {
        return bookRepository.save(entity);
    }

    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> takeBook(long id) {

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

    public Optional<Book> returnBook(long id) {
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

    public Book filter(String idOrTitle) {
        char[] chars = idOrTitle.toCharArray();
        for (char symbol : chars) {
            int x = symbol;
            if ((x >= 97 && x <= 122) || (x >= 65 && x <= 90) || (x >= 1040 && x <= 1071) || (x >= 1073 && x <= 1103)) {
                return bookRepository.getBookByTitle(idOrTitle)
                        .orElseThrow(() -> new BookNotFoundByTitleException(idOrTitle));
            }
        }
        long id = Long.parseLong(idOrTitle);
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundByIdException(id));
    }

    public Iterable<Book> getAllBooksWithFilterByTitle(String title) {
        if (title != null) {
            return new ArrayList<>(Set.of(bookRepository.getBookByTitle(title)
                    .orElseThrow(() -> new BookNotFoundByTitleException(title))));
        }
        return bookRepository.findAll();
    }
}

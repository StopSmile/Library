package com.example.Library.services;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

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
    public void deleteBookById(long id){
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

    public Optional<Book> returnBook(long id){
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

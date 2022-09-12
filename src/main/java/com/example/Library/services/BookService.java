package com.example.Library.services;

import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookService {

    private final static Integer PAGE_SIZE = 5;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> getBooksByTitle(String title) {
        return bookRepository.getBooksByTitle(title);
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

    public Page<Book> getAllBooksInPages(Integer page, String sortBy) {
        return bookRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.Direction.ASC, sortBy));
    }

    public Page<Book> getBookByTitle(String title) {
        ArrayList<Book> books = (ArrayList<Book>) bookRepository.getBooksByTitle(title);
        if (books.size() == 0) {
            throw new BookNotFoundByTitleException(title);
        } else {
            return new PageImpl<>(books);
        }
    }
}

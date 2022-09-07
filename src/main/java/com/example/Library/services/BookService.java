package com.example.Library.services;

import com.example.Library.model.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book entity) {
        return bookRepository.save(entity);
    }
    public void deleteById(long id){
        bookRepository.deleteById(id);
    }
}

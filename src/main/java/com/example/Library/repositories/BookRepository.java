package com.example.Library.repositories;

import com.example.Library.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> getBooksByTitle(String title);

}

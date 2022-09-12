package com.example.Library.repositories;

import com.example.Library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long> {
    Iterable<Book> getBooksByTitle(String title);

}

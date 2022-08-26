package com.example.Library.repositories;

import com.example.Library.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {

    Book getBookByTitle(String Title);

}

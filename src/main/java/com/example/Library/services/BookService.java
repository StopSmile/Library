package com.example.Library.services;

import com.example.Library.dto.BookDTO;
import java.util.Optional;
public interface BookService {
    Optional<BookDTO> getBookById(long id);
    Iterable<BookDTO> getBooksByTitle(String title);
    Iterable<BookDTO> getAllBooks();
    BookDTO addBook(BookDTO bookDTO);
    void deleteBookById(long id);

}

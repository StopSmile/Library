package com.example.Library.services;

import com.example.Library.dto.BookDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
public interface BookService {
    Optional<BookDTO> getBookById(long id);
    Page<BookDTO> getBookByTitle(String title);
    Page<BookDTO> getAllBooksInPages(Integer page, String sortBy);
    Iterable<BookDTO> getBooksByTitle(String title);
    Iterable<BookDTO> getAllBooks();
    BookDTO addBook(BookDTO bookDTO);
    void deleteBookById(long id);
    Optional<BookDTO> takeBook(long id);
    Optional<BookDTO> returnBook(long id);

}

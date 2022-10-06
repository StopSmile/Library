package com.example.Library.services;

import com.example.Library.dto.BookDTO;
import com.example.Library.model.Book;

public interface MappingBookService {
    BookDTO mapBookToBookDTO(Book book);
    Book mapBookDtoToBook(BookDTO bookDTO);
}

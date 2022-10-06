package com.example.Library.services.impl;

import com.example.Library.dto.BookDTO;
import com.example.Library.exceptions.EmptySecretException;
import com.example.Library.model.Book;
import com.example.Library.services.MappingBookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MappingBookServiceImpl implements MappingBookService {

    @Override
    public BookDTO mapBookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPages(book.getPages());
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setBookStatus(book.getBookStatus());
        return bookDTO;
    }

    @Override
    public Book mapBookDtoToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPages(bookDTO.getPages());
        book.setLanguage(bookDTO.getLanguage());
        book.setBookStatus(bookDTO.getBookStatus());
        book.setSecret(extractSecret(bookDTO));
        return book;
    }

    private String extractSecret(BookDTO bookDTO) {
        if (StringUtils.isNotBlank(bookDTO.getSecret())) {
            return bookDTO.getSecret();
        }
        throw new EmptySecretException();
    }
}

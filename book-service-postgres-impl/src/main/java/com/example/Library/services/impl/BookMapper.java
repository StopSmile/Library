package com.example.Library.services.impl;

import com.example.Library.enums.BookStatus;
import com.example.Library.model.Book;
import com.example.Library.dto.BookDTO;
import com.example.Library.exceptions.EmptySecretException;
import com.example.Library.model.Language;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public BookDTO mapBookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPages(book.getPages());
        bookDTO.setLanguage(getLanguageFromBook(book.getLanguage()));
        bookDTO.setBookStatus(getBookStatusFromBook(book.getBookStatus()));
        return bookDTO;
    }

    public String getLanguageFromBook(Language language) {
        if (language.getName().equals("UKRAINE")) {
            return "UKRAINE";
        }
        return "ENGLISH";
    }

    public String getBookStatusFromBook(BookStatus bookStatus) {
        if (bookStatus.name().equals("IN_THE_LIBRARY")) {
            return "IN_THE_LIBRARY";
        }
        return "NOT_IN_THE_LIBRARY";
    }


    public Book mapBookDtoToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPages(bookDTO.getPages());
        book.setLanguage(getLanguageFromBookDto(bookDTO.getLanguage()));
        book.setBookStatus(getBookStatusFromBookDto(bookDTO.getBookStatus()));
        book.setSecret(extractSecret(bookDTO));
        return book;
    }

    public Language getLanguageFromBookDto(String language) {
        if (language.equals("UKRAINE")) {
            return new Language(1, "UKRAINE");
        }
        return new Language(2, "ENGLISH");
    }

    public BookStatus getBookStatusFromBookDto(String status) {
        if (status.equals("IN_THE_LIBRARY")) {
            return BookStatus.IN_THE_LIBRARY;
        }
        return BookStatus.NOT_IN_THE_LIBRARY;
    }

    private String extractSecret(BookDTO bookDTO) {
        if (StringUtils.isNotBlank(bookDTO.getSecret())) {
            return bookDTO.getSecret();
        }
        throw new EmptySecretException();
    }
}

package com.example.Library.services.impl;

import com.example.Library.exceptions.BookAlreadyInLibrary;
import com.example.Library.exceptions.BookAlreadyInUse;
import com.example.Library.model.Book;
import com.example.Library.services.BookService;
import com.example.Library.dto.BookDTO;
import com.example.Library.enums.BookStatus;
import com.example.Library.exceptions.BookNotFoundByIdException;
import com.example.Library.exceptions.BookNotFoundByTitleException;
import com.example.Library.repositories.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
public class BookServiceImpl implements BookService {

    private final static Integer PAGE_SIZE = 5;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Optional<BookDTO> getBookById(long id) {
        log.info("Get book by id {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundByIdException(id));
        log.info("Book with {} id is found", id);
        return Optional.ofNullable(bookMapper.mapBookToBookDTO(book));
    }

    public Iterable<BookDTO> getBooksByTitle(String title) {
        log.info("Get books by title {}", title);
        return mappingBookListToBookDtoList(bookRepository.getBooksByTitle(title));
    }

    public Iterable<BookDTO> getAllBooks() {
        log.info("Get all books");
        return mappingBookListToBookDtoList(bookRepository.findAll());
    }

    public BookDTO addBook(BookDTO bookDTO) {
        log.info("Add book with id : {} author : {} title : {} pages : {} language : {}",
                bookDTO.getId(), bookDTO.getAuthor(), bookDTO.getTitle(), bookDTO.getPages(), bookDTO.getLanguage());
        Book book = bookMapper.mapBookDtoToBook(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.mapBookToBookDTO(book);
    }

    public void deleteBookById(long id) {
        log.info("Delete book with id {}", id);
        bookRepository.deleteById(id);
    }

    public Optional<BookDTO> takeBook(long id) {
        if (bookRepository.findById(id).isEmpty()) {
            log.error("Book not found by id {}", id);
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.NOT_IN_THE_LIBRARY)) {
            log.error("Book with {} already in Library", id);
            throw new BookAlreadyInUse(id);
        }
        log.info("Take book with id {}", id);
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.NOT_IN_THE_LIBRARY);
                    return bookMapper.mapBookToBookDTO(bookRepository.save(book));
                });
    }

    public Optional<BookDTO> returnBook(long id) {
        if (bookRepository.findById(id).isEmpty()) {
            log.error("Book not found by id {}", id);
            throw new BookNotFoundByIdException(id);
        }
        if (bookRepository.findById(id).get().getBookStatus().equals(BookStatus.IN_THE_LIBRARY)) {
            log.error("Book with {} already in Library", id);
            throw new BookAlreadyInLibrary(id);
        }
        log.info("Return book with id {}", id);
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookStatus(BookStatus.IN_THE_LIBRARY);
                    return bookMapper.mapBookToBookDTO(bookRepository.save(book));
                });
    }

    public Page<BookDTO> getAllBooksInPages(Integer page, String sortBy) {
        log.info("Get all books in pages. Start from page {} and sort by {}", page, sortBy);
        return bookRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.Direction.ASC, sortBy))
                .map(bookMapper::mapBookToBookDTO);
    }

    public Page<BookDTO> getBookByTitle(String title) {
        ArrayList<BookDTO> booksDto = mappingBookListToBookDtoList(bookRepository.getBooksByTitle(title));
        if (booksDto.size() == 0) {
            log.error("Book Not Found By Title : {}", title);
            throw new BookNotFoundByTitleException(title);
        } else {
            log.info("Get book by title : {}", title);
            return new PageImpl<>(booksDto);
        }
    }

    public ArrayList<BookDTO> mappingBookListToBookDtoList(Iterable<Book> bookList) {
        ArrayList<BookDTO> booksDTO = new ArrayList<>();
        for (Book x : bookList) {
            booksDTO.add(bookMapper.mapBookToBookDTO(x));
        }
        return booksDTO;
    }
}

package com.Demo.Example.ServiceImpl;

import com.Demo.Example.Configuration.ModelMapperConfig;
import com.Demo.Example.Exception.BookNotFoundException;
import com.Demo.Example.Model.Book;
import com.Demo.Example.Repository.BookRepository;
import com.Demo.Example.Service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found");
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return convertToDTO(bookOptional.get());
        } else {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

    }

    @Override
    public Book addBook(Book bookDTO) {
        if (bookDTO == null) {
            throw new IllegalArgumentException("BookDTO cannot be null");
        }

        if (bookRepository == null) {
            throw new IllegalStateException("Book repository is not initialized");
        }

        Book book = convertToEntity(bookDTO);
        if (book == null) {
            throw new IllegalArgumentException("Failed to convert BookDTO to Book entity");
        }

        try {
            Book savedBook = bookRepository.save(book);
            return convertToDTO(savedBook);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace(); // or log the exception
            return null; // or throw a custom exception, or handle differently based on your requirements
        }
    }


    @Override
    public Book updateBook(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            book.setName(book.getName());
            book.setAuthor(book.getAuthor());
            book.setCategory(book.getCategory());
            book.setEdition(book.getEdition());
            Book updatedBook = bookRepository.save(book);
            return convertToDTO(updatedBook);
        }
        return null;
    }

    @Override
    public Book partialUpdateBook(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            if (book.getName() != null) {
                book.setName(book.getName());
            }
            if (book.getAuthor() != null) {
                book.setAuthor(book.getAuthor());
            }
            if (book.getCategory() != null) {
                book.setCategory(book.getCategory());
            }
            if (book.getEdition() != null) {
                book.setEdition(book.getEdition());
            }
            Book updatedBook = bookRepository.save(book);
            return convertToDTO(updatedBook);
        }
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    private Book convertToDTO(Book book) {
        return modelMapper.map(book, Book.class);
    }

    private Book convertToEntity(Book bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }
}

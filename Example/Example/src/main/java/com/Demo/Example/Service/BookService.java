package com.Demo.Example.Service;
import com.Demo.Example.Model.Book;

import java.util.List;


public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    Book partialUpdateBook(Long id, Book book);
    void deleteBook(Long id);

}


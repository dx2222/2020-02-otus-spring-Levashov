package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.Book;

import java.util.List;

public interface BookService {

    Book insertBook(String bookName, String authortxt, String genretxt);
    Book updateBook(Long id, String bookName, String authortxt, String genretxt);
    void deleteBook(Long id);
    Book selectBookByID(Long id);

    List<Book> findAllBook();
    List<Book> findAllBookByName(String name);
    List<Book> findAllBookByAuthor(String name);
    List<Book> findAllBookByGenre(String name);
}

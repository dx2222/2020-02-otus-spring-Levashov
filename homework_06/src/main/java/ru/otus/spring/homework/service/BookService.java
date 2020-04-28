package ru.otus.spring.homework.service;

public interface BookService {

    void insertBook();
    void updateBook();
    void deleteBook();
    void selectBookByID();

    void findAllBook();
    void findAllAuthor();
    void findAllGenre();

    void findAllBookByName();
    void findAllBookByAuthor();
    void findAllBookByGenre();
}

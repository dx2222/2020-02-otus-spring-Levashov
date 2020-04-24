package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;
import java.util.List;

public interface ConsoleService {

    String readBookName();
    String readAuthor();
    String readAGenre();
    Long readBookID();
    void showBooks(List<Book> books);
    void showAuthors(List<Author> authors);
    void showGenres(List<Genre> genres);
    void showBook(Book book);

    void showBookOldAuthors(Book book);
    void showBookOldGenres(Book book);
    void showBookOld(Book book);

    void bookErrorInsert(String message);
    void bookError(String message);
    void bookNotExists();
}

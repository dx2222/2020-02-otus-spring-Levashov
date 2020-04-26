package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.*;
import java.util.List;

public interface ConsoleService {

    String readBookName();
    String readAuthor();
    String readAGenre();
    String readComment();

    Long readBookID();
    Long readCommentID();
    void showBooks(List<Book> books);
    void showAuthors(List<Author> authors);
    void showGenres(List<Genre> genres);
    void showComments(List<Comment> Comments);
    void showBook(Book book);

    void showBookOldAuthors(Book book);
    void showBookOldGenres(Book book);
    void showBookOld(Book book);

    void bookErrorInsert(String message);
    void bookError(String message);
    void commentError(String message);
    void bookNotExists();
}

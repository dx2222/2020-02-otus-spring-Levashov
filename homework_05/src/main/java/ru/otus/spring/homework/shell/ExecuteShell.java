package ru.otus.spring.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.homework.domain.TxtConst;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.MessageService;

@ShellComponent
@RequiredArgsConstructor
public class ExecuteShell {

    private final BookService bookService;
    private final MessageService messageService;


    @ShellMethod(value = "select all book", key = {"selectallbook", "sab"})
    public String selectAllBook() {
        bookService.findAllBook();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK);
    }

    @ShellMethod(value = "select all author", key = {"selectallauthor", "saa"})
    public String selectAllAuthor() {
        bookService.findAllAuthor();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_AUTHOR);
    }

    @ShellMethod(value = "select all genre", key = {"selectallgenre", "sag"})
    public String selectAllGenre() {
        bookService.findAllGenre();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_GENRE);
    }

    @ShellMethod(value = "Book insert", key = {"bookinsert", "bi"})
    public void bookInsert() {
        bookService.insertBook();
    }

    @ShellMethod(value = "select all book by name", key = {"selectallbookbyname", "sabn"})
    public String selectAllBookByName() {
        bookService.findAllBookByName();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_NAME);
    }

    @ShellMethod(value = "select all book by author", key = {"selectallbookbyauthor", "saba"})
    public String selectAllBookByAuther() {
        bookService.findAllBookByAuthor();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_AUTHOR);
    }
    @ShellMethod(value = "select all book by genre", key = {"selectallbookbygenre", "sabg"})
    public String selectAllBookByGenre() {
        bookService.findAllBookByGenre();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_GENRE);
    }

    @ShellMethod(value = "select book by ID", key = {"selectbookbyid", "sbi"})
    public void selectBookByID() {
        bookService.selectBookByID();
    }

    @ShellMethod(value = "Book delete", key = {"bookdelete", "bd"})
    public void deleteBook() {
        bookService.deleteBook();
    }

    @ShellMethod(value = "Book update", key = {"bookupdate", "bu"})
    public void updateBook() {
        bookService.updateBook();
    }
}

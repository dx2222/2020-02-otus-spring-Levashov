package ru.otus.spring.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.homework.domain.TxtConst;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;
import ru.otus.spring.homework.service.MessageService;

@ShellComponent
@RequiredArgsConstructor
public class ExecuteShell {

    private final BookService bookService;
    private final CommentService commentService;
    private final MessageService messageService;


    @ShellMethod(value = "Select all book", key = {"selectallbook", "sab"})
    public String selectAllBook() {
        bookService.findAllBook();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK);
    }

    @ShellMethod(value = "Select all author", key = {"selectallauthor", "saa"})
    public String selectAllAuthor() {
        bookService.findAllAuthor();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_AUTHOR);
    }

    @ShellMethod(value = "Select all genre", key = {"selectallgenre", "sag"})
    public String selectAllGenre() {
        bookService.findAllGenre();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_GENRE);
    }

    @ShellMethod(value = "Book insert", key = {"bookinsert", "bi"})
    public void bookInsert() {
        bookService.insertBook();
    }

    @ShellMethod(value = "Select all book by name", key = {"selectallbookbyname", "sabn"})
    public String selectAllBookByName() {
        bookService.findAllBookByName();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_NAME);
    }

    @ShellMethod(value = "Select all book by author", key = {"selectallbookbyauthor", "saba"})
    public String selectAllBookByAuther() {
        bookService.findAllBookByAuthor();
        return messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_AUTHOR);
    }
    @ShellMethod(value = "Select all book by genre", key = {"selectallbookbygenre", "sabg"})
    public String selectAllBookByGenre() {
        bookService.findAllBookByGenre();
        return  messageService.getMessage(TxtConst.SELECTED_ALL_BOOK_BY_GENRE);
    }

    @ShellMethod(value = "Select book by ID", key = {"selectbookbyid", "sbi"})
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


    @ShellMethod(value = "select comment by book", key = {"selectcommentbook", "scb"})
    public String selectCommentBook() {
        commentService.selectCommentByBookID();
        return  messageService.getMessage(TxtConst.SELECTED_COMMENT_BY_BOOK);
    }

    @ShellMethod(value = "Comment update ", key = {"commentupdate", "cu"})
    public void selectCommentUpdate() {
        commentService.updateComment();
    }
    @ShellMethod(value = "Comment insert ", key = {"commentinsert", "ci"})
    public void selectCommentInsert() {
        commentService.insertComment();
    }
    @ShellMethod(value = "Comment delete ", key = {"commentdelete", "cd"})
    public void selectCommentDelete() {
        commentService.deleteComment();
    }
}

package ru.otus.spring.homework.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.domain.*;

@Service
@RequiredArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {

    private final MessageService messageService;

    private final IOService ioService;

    @Autowired
    public ConsoleServiceImpl(IOService ioService, MessageService messageService) {
        this.ioService       = ioService;
        this.messageService  = messageService;
    }

    public String readBookName() {
        ioService.printOut(messageService.getMessage(TxtConst.BOOK_NAME));
        return ioService.readString();
    }

    public String readComment() {
        ioService.printOut(messageService.getMessage(TxtConst.COMMENT));
        return ioService.readString();
    }

    public String readBookID() {
        ioService.printOut(messageService.getMessage(TxtConst.BOOK_ID));
        return ioService.readString();
    }

    public String readCommentID() {
        ioService.printOut(messageService.getMessage(TxtConst.COMMENT_ID));
        return ioService.readString();
    }

    public String readAuthor() {
        ioService.printOut(messageService.getMessage(TxtConst.AUTHOR_NAME ));
        return ioService.readString();
    }

    public String readAGenre() {
        ioService.printOut(messageService.getMessage(TxtConst.GENRE_NAME ));
        return ioService.readString();
    }

    public void showBooks(List<Book> books) {
        if (books != null) {
            for (Book book : books) {
                ioService.printOutLn(book.getId() + " - \"" + book.getName() + "\" " + book.AuthorsToString() + " [" + book.GenresToString() + "]");
            }
        }
    }

     public void showBook(Book book) {
        if (book != null) {
            ioService.printOutLn(messageService.getMessage(TxtConst.BOOK_ID) + book.getId());
            ioService.printOutLn(messageService.getMessage(TxtConst.BOOK_NAME) + book.getName());
            ioService.printOutLn(messageService.getMessage(TxtConst.AUTHOR_NAME) + book.AuthorsToString());
            ioService.printOutLn(messageService.getMessage(TxtConst.GENRE_NAME) + book.GenresToString());
        }
    }

    public void showAuthors(List<Author> authors) {
        for (Author author:authors)  {
            ioService.printOutLn(author.getId()+" - "+author.getName());
        }
    }
    public void showGenres(List<Genre> ganres) {
        for (Genre ganre:ganres)  {
            ioService.printOutLn(ganre.getId()+" - "+ganre.getName());
        }
    }
    public void showComments(List<Comment> Comments) {
        for (Comment comment:Comments)  {
            ioService.printOutLn(comment.getId()+" - "+comment.getTextComment());
        }
    }

    public void bookErrorInsert(String message) {
        ioService.printOutLn(messageService.getMessage(TxtConst.BOOK_INSERT_ERROR) + " " + message);
    }

    public void bookError(String message) {
        ioService.printOutLn(messageService.getMessage(message));
    }

    public void commentError(String message) {
        ioService.printOutLn(messageService.getMessage(message));
    }


    public void bookNotExists() {
        ioService.printOutLn(messageService.getMessage(TxtConst.BOOK_NOTEXISTS));
    }


    public void showBookOld(Book book) {
        ioService.printOutLn(messageService.getMessage(TxtConst.BOOK_NAME_OLD)+book.getName());
    }
    public void showBookOldAuthors(Book book) {
        ioService.printOutLn(messageService.getMessage(TxtConst.AUTHOR_NAME_OLD)+book.AuthorsToString());
    }
    public void showBookOldGenres(Book book) {
        ioService.printOutLn(messageService.getMessage(TxtConst.GENRE_NAME_OLD)+book.GenresToString());
    }

}

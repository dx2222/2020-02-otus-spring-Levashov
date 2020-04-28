package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.Repository.CommentRepositoryJpa;
import ru.otus.spring.homework.domain.Comment;


import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;
    private final ConsoleService consoleService;
    private final MessageService messageService;

    @Autowired
    public CommentServiceImpl(CommentRepositoryJpa commentRepository,
                              ConsoleService consoleService,
                              MessageService messageService) {
        this.commentRepository  = commentRepository;
        this.consoleService = consoleService;
        this.messageService = messageService;
    }

    public void insertComment() {
        Long bookID = consoleService.readBookID();
        String commenttxt = consoleService.readComment();

        try {
            commentRepository.insert(Comment.builder().comment(commenttxt).book_id(bookID).build());
        } catch (EntityExistException e) {
             consoleService.commentError(e.getMessage());
        }
    }

    public void updateComment() {
        Long commentID = consoleService.readCommentID();
        Comment comment = commentRepository.getCpmmentByID(commentID);

        String commenttxt = consoleService.readComment();
        if (!commenttxt.equals("")) {
            try {
                comment.setComment(commenttxt);
                commentRepository.update(comment);
            } catch (EntityExistException e) {
                consoleService.bookErrorInsert(e.getMessage());
            }
        }
    }


    public void deleteComment() {
        Long commentID = consoleService.readCommentID();
        try {
            commentRepository.deleteByID(commentID);
        } catch (EntityExistException e) {
            consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void selectCommentByBookID() {
        Long bookID = consoleService.readBookID();
        if (bookID != 0) {
            consoleService.showComments(commentRepository.findAllByBookID(bookID));
        }
    }
}

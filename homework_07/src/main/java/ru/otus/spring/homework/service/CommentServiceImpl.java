package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Repository.CommentRepositoryJpa;
import ru.otus.spring.homework.domain.Comment;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;
    private final ConsoleService consoleService;

    @Autowired
    public CommentServiceImpl(CommentRepositoryJpa commentRepository,
                              ConsoleService consoleService) {
        this.commentRepository  = commentRepository;
        this.consoleService = consoleService;
    }

    public void insertComment() {
        Long bookID = consoleService.readBookID();
        String commenttxt = consoleService.readComment();

        try {
            commentRepository.save(Comment.builder().comment(commenttxt).bookID(bookID).build());
        } catch (Exception e) {
             consoleService.commentError(e.getMessage());
        }
    }

    public void updateComment() {
        Long commentID = consoleService.readCommentID();
        Comment comment = commentRepository.findById(commentID).orElse(null);

        String commenttxt = consoleService.readComment();
        if (!commenttxt.equals("")&&(comment != null)) {
            try {
                comment.setComment(commenttxt);
                commentRepository.save(comment);
            } catch (Exception e) {
                consoleService.bookErrorInsert(e.getMessage());
            }
        }
    }


    public void deleteComment() {
        Long commentID = consoleService.readCommentID();
        try {
            commentRepository.deleteById(commentID);
        } catch (Exception e) {
            consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void selectCommentByBookID() {
        Long bookID = consoleService.readBookID();
        if (bookID != 0) {
            consoleService.showComments(commentRepository.findByBookID(bookID));
        }
    }
}

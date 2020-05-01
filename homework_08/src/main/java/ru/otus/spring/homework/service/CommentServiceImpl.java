package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Repository.CommentRepository;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Comment;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ConsoleService consoleService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ConsoleService consoleService) {
        this.commentRepository  = commentRepository;
        this.consoleService = consoleService;
    }

    public void insertComment() {
        String bookID = consoleService.readBookID();
        String commenttxt = consoleService.readComment();

        try {
            commentRepository.save(Comment.builder().textComment(commenttxt).book(Book.builder().id(bookID).build()).build());
        } catch (Exception e) {
             consoleService.commentError(e.getMessage());
        }
    }

    public void updateComment() {
        String commentID = consoleService.readCommentID();
        Comment comment = commentRepository.findById(commentID).orElse(null);

        String commenttxt = consoleService.readComment();
        if (!commenttxt.equals("")&&(comment != null)) {
            try {
                comment.setTextComment(commenttxt);
                commentRepository.save(comment);
            } catch (Exception e) {
                consoleService.bookErrorInsert(e.getMessage());
            }
        }
    }


    public void deleteComment() {
        String commentID = consoleService.readCommentID();
        try {
            commentRepository.deleteById(commentID);
        } catch (Exception e) {
            consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void selectCommentByBookID() {
        String bookID = consoleService.readBookID();
        if (!bookID.equals("")) {
            consoleService.showComments(commentRepository.findByBookId(bookID));
        }
    }
}

package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.repository.CommentRepositoryJpa;
import ru.otus.spring.homework.domain.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepositoryJpa commentRepository) {
        this.commentRepository  = commentRepository;
    }

    public Comment insertComment(Long bookid, String comment) {
        return commentRepository.save(Comment.builder().comment(comment).bookID(bookid).build());
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> selectCommentByBookID(Long bookid) {
      return  commentRepository.findByBookID(bookid);
    }
}

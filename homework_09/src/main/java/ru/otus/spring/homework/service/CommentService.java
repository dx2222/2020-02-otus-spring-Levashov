package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.Comment;

import java.util.List;

public interface CommentService {

    void insertComment(Long bookid, String comment);
    void deleteComment(Long id);
    List<Comment> selectCommentByBookID(Long bookid);
}

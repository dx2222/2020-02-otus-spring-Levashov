package ru.otus.spring.homework.service;

public interface CommentService {

    void insertComment();
    void updateComment();
    void deleteComment();
    void selectCommentByBookID();
}

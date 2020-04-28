package ru.otus.spring.homework.Repository;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Comment;

import java.util.List;

public interface CommentRepositoryJpa {
    void insert(Comment comment) throws EntityExistException;
    void update(Comment comment) throws EntityExistException;
    void deleteByID(Long commentID) throws EntityExistException;
    List<Comment> findAll();
    List<Comment> findAllByBookID(Long bookID);
    Comment getCpmmentByID(Long commentID);
}

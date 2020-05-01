package ru.otus.spring.homework.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    void deleteByBookId(String bookId);
}

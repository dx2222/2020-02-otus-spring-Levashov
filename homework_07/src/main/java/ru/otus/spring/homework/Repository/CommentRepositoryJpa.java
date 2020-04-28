package ru.otus.spring.homework.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
    void deleteById(Long id);
    List<Comment> findAll();
    List<Comment> findByBookID(Long bookID);
    Optional<Comment> findById(Long id);
}

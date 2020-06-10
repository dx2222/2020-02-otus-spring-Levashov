package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Comment;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
    void deleteById(Long id);
    List<Comment> findAll();
    List<Comment> findByBookID(Long bookID);
    Optional<Comment> findById(Long id);
}

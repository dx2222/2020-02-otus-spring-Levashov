package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Comment;
import ru.otus.spring.homework.dto.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    @PreAuthorize("hasRole("+(char) 39+ Role.ROLE_ADMIN+(char) 39+ ") or hasPermission(#id, 'ru.otus.spring.homework.domain.Comment', 'WRITE')")
    void deleteById(Long id);

    List<Comment> findAll();
    List<Comment> findByBookID(Long bookID);
    Optional<Comment> findById(Long id);
}

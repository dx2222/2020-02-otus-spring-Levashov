package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.homework.domain.Comment;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "comment")
public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {
    @RestResource(path = "find", rel = "find")
    List<Comment> findByBookID(Long bookID);
}

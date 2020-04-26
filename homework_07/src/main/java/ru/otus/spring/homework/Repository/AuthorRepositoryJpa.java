package ru.otus.spring.homework.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.domain.Author;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository <Author, Long>{

    Author save(Author author);
    void deleteById(Long id);
    List<Author> findAll();

}

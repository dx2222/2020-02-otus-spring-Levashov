package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepositoryJpa extends JpaRepository <Author, Long>{

    Author save(Author author);
    void deleteById(Long id);
    List<Author> findAll();

}

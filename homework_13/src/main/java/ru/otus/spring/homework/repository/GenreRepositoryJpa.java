package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {

    Genre save(Genre genre);
    void deleteById(Long id);
    List<Genre> findAll();
}

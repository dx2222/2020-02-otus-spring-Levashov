package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {

}

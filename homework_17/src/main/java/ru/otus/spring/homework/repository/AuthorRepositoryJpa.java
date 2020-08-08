package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.domain.Author;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository <Author, Long>{

}

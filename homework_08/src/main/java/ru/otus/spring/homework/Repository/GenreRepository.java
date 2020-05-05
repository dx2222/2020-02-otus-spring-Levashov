package ru.otus.spring.homework.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}

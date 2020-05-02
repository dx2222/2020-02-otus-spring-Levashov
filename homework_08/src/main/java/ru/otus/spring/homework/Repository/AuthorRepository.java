package ru.otus.spring.homework.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);
}

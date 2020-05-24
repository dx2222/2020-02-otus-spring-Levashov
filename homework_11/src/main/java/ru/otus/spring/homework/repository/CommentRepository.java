package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByBookId(String bookId);
    Mono<Void> deleteByBookId(String bookId);
}

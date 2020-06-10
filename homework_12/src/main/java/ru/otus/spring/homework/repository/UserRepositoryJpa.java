package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.User;

@Repository
public interface UserRepositoryJpa extends JpaRepository <User, Long>{
    User findByUsername(String username);
}

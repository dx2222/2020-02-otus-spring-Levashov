package ru.otus.spring.homework.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Transactional
@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public void deleteByID(Long authorID) {
        Author author = em.find(Author.class,authorID);
        if (author != null) {
            em.remove(author);
        }
    }

    @Override
    public List <Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a",
                Author.class);
        return query.getResultList();
    }

}

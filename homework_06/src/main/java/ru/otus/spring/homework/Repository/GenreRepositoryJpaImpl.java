package ru.otus.spring.homework.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void update(Genre genre){
        em.merge(genre);
    }

    @Override
    public void deleteByID(Long genreID) {
        Genre genre = em.find(Genre.class,genreID);
        if (genre != null) {
            em.remove(genre);
        }
    }

    @Override
    public List <Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g order by g,name",
                Genre.class);
        return query.getResultList();
    }

}

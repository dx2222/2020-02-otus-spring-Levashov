package ru.otus.spring.homework.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.spring.homework.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void deleteByID(Long bookID) {
        Book book = em.find(Book.class,bookID);
        if (book != null) {
            em.remove(book);
        }
    }

    @Override
    public Book getBookByID(Long bookID){
        return em.find(Book.class, bookID);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "select b " +
                   "  from Book b " +
                   "  join b.author " +
                   "  join b.genre " +
                   " group by b " +
                   " order by b.name ",
                   Book.class);

        return query.getResultList();
    }

    @Override
    public List<Book> findAllByName(String name) {
        TypedQuery<Book> query = em.createQuery(
                     "select b " +
                        "  from Book b " +
                        "  join b.author " +
                        "  join b.genre " +
                        " where b.name like :name " +
                        " group by b "+
                        " order by b.name ",
                        Book.class);
        query.setParameter("name", "%"+name+"%");
        return query.getResultList();
    }

    @Override
    public List<Book> findAllByAuthor(String nameAuthor) {
        TypedQuery<Book> query = em.createQuery(
                             "select b " +
                                "  from Book b " +
                                "  join b.author a" +
                                "  join b.genre g" +
                                " where a.name like :author "+
                                " group by b " +
                                " order by b.name ",
                        Book.class);
        query.setParameter("author", "%"+nameAuthor+"%");
        return query.getResultList();
    }

    @Override
    public List<Book>findAllByGenre(String nameGenre) {
        TypedQuery<Book> query = em.createQuery(
                                "select b " +
                                   "  from Book b " +
                                   "  join b.author a" +
                                   "  join b.genre g" +
                                   " where g.name like :genre "+
                                   " group by b " +
                                   " order by b.name ",
                          Book.class);
        query.setParameter("genre", "%"+nameGenre+"%");
        return query.getResultList();
    }

}

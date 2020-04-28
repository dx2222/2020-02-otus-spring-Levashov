package ru.otus.spring.homework.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Comment comment)  {
        em.persist(comment);
    }

    @Override
    public void update(Comment comment){
        em.merge(comment);
    }

    @Override
    public void deleteByID(Long commentID) {
        Comment commen = em.find(Comment.class,commentID);
        if (commen != null) {
            em.remove(commen);
        }
    }

    @Override
    public List <Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c order by c.id",
                Comment.class);
        return query.getResultList();
    }
    @Override
    public List<Comment> findAllByBookID(Long bookID) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.book_id = :bookid order by c.id",
                Comment.class);
        query.setParameter("bookid", bookID);
        return query.getResultList();
    }

    @Override
    public Comment getCpmmentByID(Long commentID) {
        return em.find(Comment.class,commentID);
    }

}

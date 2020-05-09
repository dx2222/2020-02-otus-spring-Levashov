package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends JpaRepository<Book, Long> {

    Book save(Book book);

    void deleteById(Long aLong);
    Optional<Book> findById(Long id);
    List <Book> findAll();


    List <Book> findByNameLike(String name);

    @Query("select b " +
           "  from Book b " +
           "  join b.author a" +
           "  join b.genre g" +
           " where a.name like :author "+
           " group by b " +
           " order by b.name ")
    List <Book> findAllByAuthor(@Param("author") String nameAuthor);

    @Query("select b " +
            "  from Book b " +
            "  join b.author a" +
            "  join b.genre g" +
            " where g.name like :genre "+
            " group by b " +
            " order by b.name ")
    List <Book> findAllByGenre(@Param("genre") String nameGenre);

}

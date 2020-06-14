package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.dto.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepositoryJpa extends JpaRepository<Book, Long> {

    Book save(Book book);

    @PreAuthorize("hasRole("+(char) 39+ Role.ROLE_ADMIN+(char) 39+ ") or hasPermission(#id, 'ru.otus.spring.homework.domain.Book', 'WRITE')")
    void deleteById(Long id);

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

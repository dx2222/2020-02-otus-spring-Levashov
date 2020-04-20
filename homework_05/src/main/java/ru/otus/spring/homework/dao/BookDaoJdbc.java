package ru.otus.spring.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.Exceptions.*;

import ru.otus.spring.homework.domain.*;
import ru.otus.spring.homework.service.MessageService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final MessageService messageService;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,  MessageService messageService) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.messageService = messageService;
    }

    @Override
    public Book insert(Book book) throws BookExistException{
            if (countByName(book.getName()) == 0) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("name", book.getName());
                GeneratedKeyHolder key = new GeneratedKeyHolder();

                namedParameterJdbcOperations.update(
                        "insert into TBOOK (name) values (:name)",
                        params, key);
                book.setBookID((Long) key.getKey());

                return book;
            } else {
                throw new BookExistException(messageService.getMessage(TxtConst.BOOK_EXISTS_ERROR,new String [] {book.getName()}));
            }
    }

    @Override
    public void update(Book book) throws BookExistException{
        if (countByID(book.getBookID()) == 1) {

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", book.getBookID());
            params.addValue("name", book.getName());

            namedParameterJdbcOperations.update(
                    "update TBOOK " +
                            "set name = :name " +
                            "where bookID = :bookID",
                    params);

        } else {
            throw new BookExistException(messageService.getMessage(TxtConst.BOOK_EXISTS_ERROR,new String [] {book.getName()}));
        }
    }

    @Override
    public void deleteByID(Long bookID) {
        if (countByID(bookID) == 1) {
            namedParameterJdbcOperations.update(
                    "delete from TBOOK where bookID = :bookID",
                    Collections.singletonMap("bookID", bookID));
        }
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations
                .getJdbcOperations()
                .query("select bookID, name " +
                        "  from TBOOK " +
                        " order by name", new BookMapper());
    }

    @Override
    public List<Book> findAllByName(String name) {
        return namedParameterJdbcOperations
                .query("select bookID, name " +
                                "  from TBOOK " +
                                " where name like :name"+
                                " order by name",
                        Collections.singletonMap("name", "%"+name+"%"), new BookMapper());
    }

    @Override
    public List<Book> findAllByAuthor(String nameAuthor) {
        return namedParameterJdbcOperations
                .query("select distinct b.bookID, b.name " +
                                "  from  TAUTHOR a" +
                                " inner join TBOOKAUTHOR ba" +
                                "   on ba.AuthorID = a.AuthorID" +
                                " inner join TBOOK b" +
                                "    on b.BookID = ba.BookID" +
                                " where a.name like :author"+
                                " order by b.name",
                        Collections.singletonMap("author", "%"+nameAuthor+"%"), new BookMapper());
    }

    @Override
    public List<Book>findAllByGenre(String nameGenre) {
        return namedParameterJdbcOperations
                .query("select distinct b.bookID, b.name " +
                                "  from  TGENRE g" +
                                " inner join TBOOKGENRE bg" +
                                "   on bg.GenreID = g.GenreID" +
                                " inner join TBOOK b" +
                                "    on b.BookID = bg.BookID" +
                                " where g.name like :genre"+
                                " order by b.name",
                        Collections.singletonMap("genre", "%"+nameGenre+"%"), new BookMapper());
    }


    @Override
    public Book getBookByID(Long bookID){
        return namedParameterJdbcOperations.queryForObject(
                       "select bookID, name " +
                          "  from TBOOK " +
                          " where bookID = :bookID",
                      Collections.singletonMap("bookID", bookID), new BookMapper());
    }

    @Override
    public int countByID(Long bookID) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOK where bookID = :bookID",
                Collections.singletonMap("bookID", bookID), Integer.class);
    }

    @Override
    public int countByName(String name) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOK where name = :name",
                Collections.singletonMap("name", name), Integer.class);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long bookID   = resultSet.getLong("bookID");
            String name   = resultSet.getString("name");
            return new Book(bookID, name, null,null);
        }
    }


}

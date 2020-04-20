package ru.otus.spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookAuthorDaoJdbc implements BookAuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookAuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public void insertBookAuthor(Long bookID, Long authorID){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("authorID", authorID);

        namedParameterJdbcOperations.update(
                "insert into TBOOKAUTHOR(bookID, authorID) values(:bookID,:authorID)",
                params);
    }

    @Override
    public void deleteByID(Long bookID, Long authorID) {
        if (countByID(bookID, authorID) == 1) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", bookID);
            params.addValue("authorID", authorID);

            namedParameterJdbcOperations.update(
                    "delete from TBOOKAUTHOR where bookID = :bookID and authorID = :authorID",
                    params );
        }
    }

    @Override
    public void deleteByBookID(Long bookID) {
        namedParameterJdbcOperations.update(
                "delete from TBOOKAUTHOR where bookID = :bookID",
                Collections.singletonMap("bookID", bookID) );
    }

    @Override
    public int countByID(Long bookID, Long authorID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("authorID", authorID);

        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOKAUTHOR where bookID = :bookID and authorID = :authorID",
                params, Integer.class);
    }

    @Override
    public List<Author> findByBookID(Long bookID) {
        return namedParameterJdbcOperations.query(
                "select * " +
                   "  from TBOOKAUTHOR ba" +
                   " inner join TAUTHOR a" +
                   "    on a.authorID = ba.authorID" +
                   " where ba.bookID = :bookID"+
                   " order by a.name",
                Collections.singletonMap("bookID", bookID), new AuthorMapper());
    }

    @Override
    public List<Book> findByAuthorID(Long authorID){
        return namedParameterJdbcOperations.query(
                "select * " +
                        "  from TBOOKAUTHOR ba" +
                        " inner join TBOOK b" +
                        "    on b.bookID = ba.bookID" +
                        " where ba.authorID = :authorID"+
                        " order by b.name",
                Collections.singletonMap("authorID", authorID), new BookMapper());
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Long authorID  = resultSet.getLong("authorID");
            String name    = resultSet.getString("name");
            return new Author(authorID, name);
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long bookID  = resultSet.getLong("bookID");
            String name    = resultSet.getString("name");
            return new Book(bookID, name, null,null);
        }
    }

}

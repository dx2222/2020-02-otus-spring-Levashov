package ru.otus.spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author insert(Author author) throws EntityExistException {
        if (countByName(author.getName()) == 0){
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", author.getName());
            GeneratedKeyHolder key = new GeneratedKeyHolder();

            namedParameterJdbcOperations.update(
                    "insert into TAUTHOR (`name`) values (:name)",
                    params, key);

            author.setAuthorID((Long) key.getKey());
            return author;
        } else {
            throw new EntityExistException("Error author insert. Author exists "+author.getName());
        }

    }

    @Override
    public void update(Author author){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authorID", author.getAuthorID());
        params.addValue("name", author.getName());

        namedParameterJdbcOperations.update(
                "update TAUTHOR set name = :name where authorID = :authorID",
                params);
    }

    @Override
    public void deleteByID(Long authorID) {
        if (countByID(authorID) == 1) {
            namedParameterJdbcOperations.update(
                    "delete from TAUTHOR where authorID = :authorID",
                    Collections.singletonMap("authorID", authorID));
        }
    }

    @Override
    public List <Author> findAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select * from TAUTHOR order by name", new AuthorMapper());
    }

    @Override
    public int countByName(String name) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TAUTHOR where name = :name",
                Collections.singletonMap("name", name), Integer.class);
    }

    @Override
    public int countByID(Long authorID) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TAUTHOR where authorID = :authorID",
                Collections.singletonMap("authorID", authorID), Integer.class);
    }

    @Override
    public Author getAuthorByName(String name) {
        try {
                return namedParameterJdbcOperations.queryForObject(
                        "select authorID, name from TAUTHOR where name = :name",
                         Collections.singletonMap("name", name), new AuthorMapper());
            } catch (Exception e) {
                 return null;
            }
    }

    @Override
    public List<Author> findAuthorByBookID(Long bookID) {
        return namedParameterJdbcOperations.query(
                "select a.authorID, a.name  " +
                        "  from TBOOKAUTHOR ba" +
                        " inner join TAUTHOR a" +
                        "    on a.authorID = ba.authorID" +
                        " where ba.bookID = :bookID"+
                        " order by a.name",
                Collections.singletonMap("bookID", bookID), new AuthorDaoJdbc.AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Long authorID  = resultSet.getLong("authorID");
            String name    = resultSet.getString("name");
            return new Author(authorID, name);
        }
    }
}

package ru.otus.spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookGenreDaoJdbc implements BookGenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookGenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insertBookGenre(Long bookID, Long genreID){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("genreID", genreID);

        namedParameterJdbcOperations.update(
                "insert into TBOOKGENRE (bookID, genreID) values(:bookID,:genreID)", params
        );
    }

    @Override
    public void deleteByID(Long bookID, Long genreID) {
        if (countByID(bookID, genreID) == 1) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", bookID);
            params.addValue("genreID", genreID);

            namedParameterJdbcOperations.update(
                    "delete from TBOOKGENRE where bookID = :bookID and genreID = :genreID",
                    params );
        }
    }

    @Override
    public void deleteByBookID(Long bookID) {
        namedParameterJdbcOperations.update(
                "delete from TBOOKGENRE where bookID = :bookID",
                Collections.singletonMap("bookID", bookID) );
    }

    @Override
    public int countByID(Long bookID, Long genreID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("genreID", genreID);

        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOKGENRE where where bookID = :bookID and genreID = :genreID",
                params, Integer.class);
    }

    @Override
    public List<Genre> findByBookID(Long bookID) {
        return namedParameterJdbcOperations.query(
                "select * " +
                   "  from TBOOKGENRE ba" +
                   " inner join TGENRE g" +
                   "    on g.genreID = ba.genreID" +
                   " where ba.bookID = :bookID"+
                   " order by g.name",
                Collections.singletonMap("bookID", bookID), new GenreMapper());
    }

    @Override
    public List<Book> findByGenreID(Long genreID){
        return namedParameterJdbcOperations.query(
                "select * " +
                        "  from TBOOKGENRE ba" +
                        " inner join TBOOK b" +
                        "    on b.bookID = ba.bookID" +
                        " where ba.genreID = :genreID"+
                        " order by b.name",
                Collections.singletonMap("genreID", genreID), new BookMapper());
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Long genreID  = resultSet.getLong("genreID");
            String name    = resultSet.getString("name");
            return new Genre(genreID, name);
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

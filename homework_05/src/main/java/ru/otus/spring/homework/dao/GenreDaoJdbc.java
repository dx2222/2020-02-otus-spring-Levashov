package ru.otus.spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.Exceptions.*;
import ru.otus.spring.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Genre insert(Genre genre) throws EntityExistException{
        if (countByName(genre.getName()) == 0){
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", genre.getName());
            GeneratedKeyHolder key = new GeneratedKeyHolder();

            namedParameterJdbcOperations.update(
                    "insert into TGENRE (`name`) values (:name)",
                    params, key);
            genre.setGenreID((Long) key.getKey());
            return genre;
        } else  {
            throw new EntityExistException("Error genre insert. Genre exists "+genre.getName());
        }
    }

    @Override
    public void update(Genre genre){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreID", genre.getGenreID());
        params.addValue("name", genre.getName());

        namedParameterJdbcOperations.update(
                "update TGENRE set name = :name where genreID = :genreID",
                params);
    }

    @Override
    public void deleteByID(Long genreID) {
        if (countByID(genreID) == 1) {
            namedParameterJdbcOperations.update(
                    "delete from TGENRE where genreID = :genreID",
                    Collections.singletonMap("genreID", genreID) );
        }
    }

    @Override
    public List <Genre> findAll() {
            return namedParameterJdbcOperations.getJdbcOperations().query("select * from TGENRE order by name", new GenreMapper());
    }

    @Override
    public int countByName(String name) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TGENRE where name = :name",
                Collections.singletonMap("name", name), Integer.class);
    }

    @Override
    public int countByID(Long genreID) {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TGENRE where genreID = :genreID",
                Collections.singletonMap("genreID", genreID), Integer.class);
    }

    @Override
    public Genre getGenreByName(String name) {
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select genreID, name from TGENRE where name = :name",
                    Collections.singletonMap("name", name), new GenreMapper());
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Genre> findGenreByBookID(Long bookID) {
        return namedParameterJdbcOperations.query(
                "select g.genreID, g.name " +
                        "  from TBOOKGENRE ba" +
                        " inner join TGENRE g" +
                        "    on g.genreID = ba.genreID" +
                        " where ba.bookID = :bookID"+
                        " order by g.name",
                Collections.singletonMap("bookID", bookID), new GenreDaoJdbc.GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Long genreID   = resultSet.getLong("genreID");
            String name    = resultSet.getString("name");
            return new Genre(genreID, name);
        }
    }

}

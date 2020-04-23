package ru.otus.spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.Exceptions.*;

import ru.otus.spring.homework.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,
                       AuthorDao authorDao,
                       GenreDao genreDao) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao  = genreDao;
    }

    private Book getBookList(Book book) {
        if (book != null) {
            book.setAuthors(authorDao.findAuthorByBookID(book.getBookID()));
            book.setGenres(genreDao.findGenreByBookID(book.getBookID()));
        }
        return book;
    }


    private List<Book> getLists(List<Book> books) {
        if (books != null) {
            for (Book curBook : books) {
                getBookList(curBook);
            }
        }
        return books;
    }

    @Override
    public Book insert(Book book) throws EntityExistException {
            if (countByName(book.getName()) == 0) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("name", book.getName());
                GeneratedKeyHolder key = new GeneratedKeyHolder();

                namedParameterJdbcOperations.update(
                        "insert into TBOOK (name) values (:name)",
                        params, key);
                book.setBookID((Long) key.getKey());

                deleteBookAuthorByBookID(book.getBookID());
                for (Author curAuthor:book.getAuthors()) {
                    if (authorDao.getAuthorByName(curAuthor.getName()) == null) {
                        try {
                            Author author = authorDao.insert(curAuthor);
                            insertBookAuthor(book.getBookID(),author.getAuthorID());
                        } catch (EntityExistException e) {
                            throw new EntityExistException(e.getMessage()+" book "+book.getName());
                        }
                    }
                }

                deleteBookGenreByBookID(book.getBookID());

                for (Genre curGenre:book.getGenres()) {
                    if (genreDao.getGenreByName(curGenre.getName()) == null) {
                        try {
                            Genre genre = genreDao.insert(curGenre);
                            insertBookGenre(book.getBookID(),genre.getGenreID());
                        } catch (EntityExistException e) {
                            throw new EntityExistException(e.getMessage()+" book "+book.getName());
                        }
                    }
                }


                return book;
            } else {
                throw new EntityExistException("Error book insert. Book exists "+ book.getName());
            }
    }

    @Override
    public void update(Book book) throws EntityExistException {
        if (countByID(book.getBookID()) == 1) {

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", book.getBookID());
            params.addValue("name", book.getName());

            namedParameterJdbcOperations.update(
                    "update TBOOK " +
                            "set name = :name " +
                            "where bookID = :bookID",
                    params);

            deleteBookAuthorByBookID(book.getBookID());

            for (Author curAuthor:book.getAuthors()) {
                if (authorDao.getAuthorByName(curAuthor.getName()) == null) {
                    try {
                        Author author = authorDao.insert(curAuthor);
                        insertBookAuthor(book.getBookID(),author.getAuthorID());
                    } catch (EntityExistException e) {
                        throw new EntityExistException(e.getMessage()+" book "+book.getName());
                    }
                }
            }

            deleteBookGenreByBookID(book.getBookID());

            for (Genre curGenre:book.getGenres()) {
                if (genreDao.getGenreByName(curGenre.getName()) == null) {
                    try {
                        Genre genre = genreDao.insert(curGenre);
                        insertBookGenre(book.getBookID(),genre.getGenreID());
                    } catch (EntityExistException e) {
                        throw new EntityExistException(e.getMessage()+" book "+book.getName());
                    }
                }
            }

        } else {
            throw new EntityExistException("Error book update. Book not exists "+book.getName());
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
        return getLists(namedParameterJdbcOperations
                .getJdbcOperations()
                .query("select bookID, name " +
                        "  from TBOOK " +
                        " order by name", new BookMapper()));
    }

    @Override
    public List<Book> findAllByName(String name) {
        return getLists(namedParameterJdbcOperations
                .query("select bookID, name " +
                                "  from TBOOK " +
                                " where name like :name"+
                                " order by name",
                        Collections.singletonMap("name", "%"+name+"%"), new BookMapper()));
    }

    @Override
    public List<Book> findAllByAuthor(String nameAuthor) {
        return getLists(namedParameterJdbcOperations
                .query("select distinct b.bookID, b.name " +
                                "  from  TAUTHOR a" +
                                " inner join TBOOKAUTHOR ba" +
                                "   on ba.AuthorID = a.AuthorID" +
                                " inner join TBOOK b" +
                                "    on b.BookID = ba.BookID" +
                                " where a.name like :author"+
                                " order by b.name",
                        Collections.singletonMap("author", "%"+nameAuthor+"%"), new BookMapper()));
    }

    @Override
    public List<Book>findAllByGenre(String nameGenre) {
        return getLists(namedParameterJdbcOperations
                .query("select distinct b.bookID, b.name " +
                                "  from  TGENRE g" +
                                " inner join TBOOKGENRE bg" +
                                "   on bg.GenreID = g.GenreID" +
                                " inner join TBOOK b" +
                                "    on b.BookID = bg.BookID" +
                                " where g.name like :genre"+
                                " order by b.name",
                        Collections.singletonMap("genre", "%"+nameGenre+"%"), new BookMapper()));
    }


    @Override
    public Book getBookByID(Long bookID){
        return getBookList(namedParameterJdbcOperations.queryForObject(
                       "select bookID, name " +
                          "  from TBOOK " +
                          " where bookID = :bookID",
                      Collections.singletonMap("bookID", bookID), new BookMapper()));
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
    public void deleteBookAuthorByID(Long bookID, Long authorID) {
        if (countBookAuthorByID(bookID, authorID) == 1) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", bookID);
            params.addValue("authorID", authorID);

            namedParameterJdbcOperations.update(
                    "delete from TBOOKAUTHOR where bookID = :bookID and authorID = :authorID",
                    params );
        }
    }

    @Override
    public void deleteBookAuthorByBookID(Long bookID) {
        namedParameterJdbcOperations.update(
                "delete from TBOOKAUTHOR where bookID = :bookID",
                Collections.singletonMap("bookID", bookID) );
    }

    @Override
    public int countBookAuthorByID(Long bookID, Long authorID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("authorID", authorID);

        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOKAUTHOR where bookID = :bookID and authorID = :authorID",
                params, Integer.class);
    }


    @Override
    public List<Book> findBookByAuthorID(Long authorID){
        return namedParameterJdbcOperations.query(
                "select b.bookID, b.name" +
                        "  from TBOOKAUTHOR ba" +
                        " inner join TBOOK b" +
                        "    on b.bookID = ba.bookID" +
                        " where ba.authorID = :authorID"+
                        " order by b.name",
                Collections.singletonMap("authorID", authorID), new BookDaoJdbc.BookMapper());
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
    public void deleteBookGenreByID(Long bookID, Long genreID) {
        if (countBookGenreByID(bookID, genreID) == 1) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("bookID", bookID);
            params.addValue("genreID", genreID);

            namedParameterJdbcOperations.update(
                    "delete from TBOOKGENRE where bookID = :bookID and genreID = :genreID",
                    params );
        }
    }

    @Override
    public void deleteBookGenreByBookID(Long bookID) {
        namedParameterJdbcOperations.update(
                "delete from TBOOKGENRE where bookID = :bookID",
                Collections.singletonMap("bookID", bookID) );
    }

    @Override
    public int countBookGenreByID(Long bookID, Long genreID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookID", bookID);
        params.addValue("genreID", genreID);

        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from TBOOKGENRE where where bookID = :bookID and genreID = :genreID",
                params, Integer.class);
    }

    @Override
    public List<Book> findBookByGenreID(Long genreID){
        return namedParameterJdbcOperations.query(
                "select * " +
                        "  from TBOOKGENRE ba" +
                        " inner join TBOOK b" +
                        "    on b.bookID = ba.bookID" +
                        " where ba.genreID = :genreID"+
                        " order by b.name",
                Collections.singletonMap("genreID", genreID), new BookDaoJdbc.BookMapper());
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

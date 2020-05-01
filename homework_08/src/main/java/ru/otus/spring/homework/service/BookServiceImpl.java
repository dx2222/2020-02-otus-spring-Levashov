package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Repository.*;
import ru.otus.spring.homework.domain.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ConsoleService consoleService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           ConsoleService consoleService) {
        this.bookRepository   = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository  = genreRepository;
        this.consoleService = consoleService;
    }

    private List<Author> getAuthorList(List<Author> authorList) {

        List<Author>  result = new ArrayList<Author>();
        for(Author author:authorList) {
            Author authorf = authorRepository.findByName(author.getName()).orElse(null);
            if (authorf == null) {
                authorRepository.save(author);
                authorf = authorRepository.findByName(author.getName()).orElse(null);
            }
            result.add(authorf);
        }
       return result;
    }

    private List<Genre> getGenreList(List<Genre> genreList) {

        List<Genre>  result = new ArrayList<Genre>();
        for(Genre genre:genreList) {
            Genre genref = genreRepository.findByName(genre.getName()).orElse(null);
            if (genref == null) {
                genreRepository.save(genre);
                genref = genreRepository.findByName(genre.getName()).orElse(null);
            }
            result.add(genref);
        }
        return result;
    }


    public void insertBook() {
        String bookName = consoleService.readBookName();

        List<Author> authors = Author.getAuthorList(consoleService.readAuthor().split(","));

        List<Genre>  genres = Genre.getGenreList(consoleService.readAGenre().split(","));


        try {
            Book book = bookRepository.save(Book.builder().name(bookName).author(getAuthorList(authors)).genre(getGenreList(genres)).build());

            consoleService.showBook(bookRepository.findByName(book.getName()).orElse(null));

        } catch (Exception e) {
             consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void updateBook() {
        String bookID = consoleService.readBookID();
        Book book = bookRepository.findById(bookID).orElse(null);
        boolean updateed = false;

        if (book != null) {
            consoleService.showBookOld(book);
            String bookName = consoleService.readBookName();
            if (!bookName.trim().equals("")) {
                book.setName(bookName);
                updateed = true;
            }

            consoleService.showBookOldAuthors(book);
            String authors = consoleService.readAuthor();
            if (!authors.trim().equals("")) {
                List<Author> authorsList = Author.getAuthorList(authors.split(","));

                book.setAuthor(getAuthorList(authorsList));
                updateed = true;
            }


            consoleService.showBookOldGenres(book);
            String genres = consoleService.readAGenre();
            if (!genres.trim().equals("")) {
                List<Genre> genresList = Genre.getGenreList(genres.split(","));

                book.setGenre(getGenreList(genresList));
                updateed = true;
            }

            if (updateed) {
                try {
                    bookRepository.save(book);

                    consoleService.showBook(bookRepository.findById(bookID).orElse(null));

                } catch (Exception e) {
                    consoleService.bookErrorInsert(e.getMessage());
                }
            }
        }
    }


    public void deleteBook() {
        String bookID = consoleService.readBookID();
        Book book =  bookRepository.findById(bookID).orElse(null);

        if (book != null) {
            bookRepository.deleteById(book.getId());
        } else {
            consoleService.bookNotExists();
        }
    }

    public void selectBookByID(){
        String bookID = consoleService.readBookID();
      Book book =  bookRepository.findById(bookID).orElse(null);
      if (book != null) {
          consoleService.showBook(book);
      } else {
          consoleService.bookNotExists();
      }
    }


    public void findAllBook() {
        consoleService.showBooks(bookRepository.findAll());
    }

    public void findAllAuthor() {
        consoleService.showAuthors(authorRepository.findAll());
    }

    public void findAllGenre() {
        consoleService.showGenres(genreRepository.findAll());
    }

    public void findAllBookByName() {
        String bookName = consoleService.readBookName();
        consoleService.showBooks(bookRepository.findByNameLike(bookName));
    }

    public void findAllBookByAuthor() {
        String author = consoleService.readAuthor();
        List<Author> authors = authorRepository.findByNameLike(author);

        List<String> authorsid = new ArrayList<>();
        for (Author auther1:authors) {
            authorsid.add(auther1.getId());
        }

        consoleService.showBooks(bookRepository.findByAuthorIdIn(authorsid));
    }

    public void findAllBookByGenre() {
        String genre = consoleService.readAGenre();
        List<Genre> genres = genreRepository.findByNameLike(genre);

        List<String> genresid = new ArrayList<>();
        for (Genre genre1:genres) {
            genresid.add(genre1.getId());
        }

        consoleService.showBooks(bookRepository.findByGenreIdIn(genresid));
    }


}

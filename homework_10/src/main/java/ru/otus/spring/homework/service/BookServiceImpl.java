package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.repository.*;
import ru.otus.spring.homework.domain.*;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;

    @Autowired
    public BookServiceImpl(BookRepositoryJpa bookRepository) {
        this.bookRepository   = bookRepository;
    }

    public Book insertBook(String bookName, String authortxt, String genretxt) {
        int cntattr = 0;
        Book book = new Book();
        Book newBook = null;

        if (!bookName.trim().equals("")) {
            book.setName(bookName);
            cntattr++;
        }
        if (!authortxt.trim().equals("")) {
            List<Author> authorsList = Author.getAuthorList(authortxt.split(","));
            book.setAuthor(authorsList);
            cntattr++;
        }
        if (!genretxt.trim().equals("")) {
            List<Genre> genresList = Genre.getGenreList(genretxt.split(","));
            book.setGenre(genresList);
            cntattr++;
        }

        if (cntattr == 3) {
           newBook = bookRepository.save(book);
        }
        return newBook;
    }

    public Book updateBook(Long id, String bookName, String authortxt, String genretxt) {
        boolean updateed = false;
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            if ((!bookName.trim().equals(""))&&(!book.getName().equals(bookName))) {
                book.setName(bookName);
                updateed = true;
            }
            if ((!authortxt.trim().equals(""))&&(!book.authorsToString().equals(authortxt))) {
                List<Author> authorsList = Author.getAuthorList(authortxt.split(","));
                book.setAuthor(authorsList);
                updateed = true;
            }

            if ((!genretxt.trim().equals(""))&&(!book.genresToString().equals(genretxt))) {
                List<Genre> genresList = Genre.getGenreList(genretxt.split(","));
                book.setGenre(genresList);
                updateed = true;
            }
        }

        if (updateed) {
            return bookRepository.save(book);
        } else {
            return book;
        }
    }


    public void deleteBook(Long id) {
        bookRepository.findById(id).ifPresent(book -> bookRepository.deleteById(book.getId()));
    }

    public Book selectBookByID(Long id){
      return  bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }


    public List<Book> findAllBookByName(String name) {
        return bookRepository.findByNameLike('%'+name+"%");
    }

    public List<Book> findAllBookByAuthor(String name) {
        return bookRepository.findAllByAuthor('%'+name+"%");
    }

    public List<Book> findAllBookByGenre(String name) {
        return bookRepository.findAllByGenre("%"+name+"%");
    }


}

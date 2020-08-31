package ru.otus.spring.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.repository.*;
import ru.otus.spring.homework.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;

    @Autowired
    public BookServiceImpl(BookRepositoryJpa bookRepository) {
        this.bookRepository   = bookRepository;
    }

    @HystrixCommand(commandKey="insertBook", fallbackMethod="buildFallbackBook")
    public Book insertBook(String bookName, String authortxt, String genretxt) {
        randomSleep();
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

    @HystrixCommand(commandKey="updateBook", fallbackMethod="buildFallbackBook")
    public Book updateBook(Long id, String bookName, String authortxt, String genretxt) {
        randomSleep();
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

    @HystrixCommand(commandKey="selectBookByID", fallbackMethod="buildFallbackBook")
    public Book selectBookByID(Long id){
        randomSleep();
        return  bookRepository.findById(id).orElse(null);
    }

    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    public List<Book> findAllBook() {
        randomSleep();
        return bookRepository.findAll();
    }
    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    public List<Book> findAllBookByName(String name) {
        randomSleep();
        return bookRepository.findByNameLike('%'+name+"%");
    }
    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    public List<Book> findAllBookByAuthor(String name) {
        randomSleep();
        return bookRepository.findAllByAuthor('%'+name+"%");
    }
    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    public List<Book> findAllBookByGenre(String name) {
        randomSleep();
        return bookRepository.findAllByGenre("%"+name+"%");
    }

    public void randomSleep() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) ;
        if(randomNum == 1) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> buildFallbackBookList() {
        List<Book> listBook = new ArrayList<>();
        listBook.add(Book.builder().id(0L).name("Error DB!!!").build());
        return listBook;
    }

    public List<Book> buildFallbackBookList(String name) {
        List<Book> listBook = new ArrayList<>();
        listBook.add(Book.builder().id(0L).name("Error DB!!!").build());
        return listBook;
    }



    public Book buildFallbackBook(String bookName, String authortxt, String genretxt) {
        return Book.builder().id(0L).name("Error DB!!!").build();
    }
    public Book buildFallbackBook(Long id, String bookName, String authortxt, String genretxt) {
        return Book.builder().id(0L).name("Error DB!!!").build();
    }
    public Book buildFallbackBook(Long id) {
        return Book.builder().id(0L).name("Error DB!!!").build();
    }
}

package ru.otus.spring.homework.restcontroller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.homework.dto.BookDto;
import ru.otus.spring.homework.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService     = bookService;
    }


    public void randomSleep() {
        Random rand = new Random();
        int randomNum = rand.nextInt(2) ;
        if(randomNum == 1) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    @GetMapping("/book")
    public List<BookDto>  bookAll() {

        randomSleep();
        return bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto bookByID(@PathVariable Long id) {
        return BookDto.toDto(bookService.selectBookByID(id));
    }

    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    @GetMapping("/book/selectby")
    public List<BookDto> bookSelectBy(@RequestParam("FindText") String findText,
                                      @RequestParam("cbFindType") String findType) {
        randomSleep();

        List<BookDto> books;
        switch (findType) {
            case "name":
                books = bookService.findAllBookByName(findText).stream().map(BookDto::toDto).collect(Collectors.toList());
                break;
            case "author":
                books = bookService.findAllBookByAuthor(findText).stream().map(BookDto::toDto).collect(Collectors.toList());
                break;
            case "genre":
                books = bookService.findAllBookByGenre(findText).stream().map(BookDto::toDto).collect(Collectors.toList());
                break;
            default:
                books = bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
                break;
        }
        return books;
    }

    @PostMapping("/book")
    public BookDto bookInset(@RequestBody BookDto book) {
        return BookDto.toDto(bookService.insertBook(book.getName(), book.getAuthors(), book.getGenres()));
    }

    @PutMapping("/book")
    public BookDto bookUpdate(@RequestBody BookDto book) {
        return BookDto.toDto(bookService.updateBook(book.getId(), book.getName(), book.getAuthors(), book.getGenres()));
    }

    @DeleteMapping("/book/{id}")
    public void bookDelete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @ExceptionHandler()
    public ModelAndView handleNPE(NullPointerException e) {
        ModelAndView modelAndView = new ModelAndView("err500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    public List<BookDto> buildFallbackBookList() {
        List<BookDto> listBookDto = new ArrayList<>();
        listBookDto.add(BookDto.builder().id(0L).name("Error DB!!!").build());
        return listBookDto;
    }


}

package ru.otus.spring.homework.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.homework.dto.BookDto;
import ru.otus.spring.homework.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService     = bookService;
    }





    @GetMapping("/book")
    public List<BookDto>  bookAll() {

        return bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto bookByID(@PathVariable Long id) {
        return BookDto.toDto(bookService.selectBookByID(id));
    }

    @GetMapping("/book/selectby")
    public List<BookDto> bookSelectBy(@RequestParam("FindText") String findText,
                                      @RequestParam("cbFindType") String findType) {

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

}

package ru.otus.spring.homework.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.homework.dto.BookDto;
import ru.otus.spring.homework.dto.CommentDto;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public BookRestController(BookService bookService,
                              CommentService commentService) {
        this.bookService     = bookService;
        this.commentService  = commentService;
    }

    @GetMapping("/books")
    public List<BookDto>  booksPage() {
        return bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto bookPage(@PathVariable Long id) {
        return BookDto.toDto(bookService.selectBookByID(id));
    }

    @GetMapping("/books/selectby")
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

    @GetMapping("/book/selectinsert")
    public BookDto bookInsetSelect() {
        return BookDto.builder().id(0L).name("").authors("").genres("").build();
    }

    @PostMapping("/book/insert")
    public BookDto bookInset(@RequestBody BookDto book) {
        return BookDto.toDto(bookService.insertBook(book.getName(), book.getAuthors(), book.getGenres()));
    }

    @PostMapping("/book/update")
    public BookDto bookUpdate(@RequestBody BookDto book) {
        return BookDto.toDto(bookService.updateBook(book.getId(), book.getName(), book.getAuthors(), book.getGenres()));
    }

    @DeleteMapping("/book/delete/{id}")
    public void bookDelete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/comments/{id}")
    public List<CommentDto> CommentPage(@PathVariable Long id) {
        return commentService.selectCommentByBookID(id).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/comment/insert")
    public CommentDto commentInsert(@RequestBody CommentDto comment) {
        return CommentDto.toDto(commentService.insertComment(comment.getBookID(), comment.getComment()));
    }

    @DeleteMapping("/comment/delete/{id}")
    public void commentDelete(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @ExceptionHandler()
    public ModelAndView handleNPE(NullPointerException e) {
        ModelAndView modelAndView = new ModelAndView("err500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

}

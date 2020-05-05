package ru.otus.spring.homework.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.homework.domain.*;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;
import java.util.List;

@Controller
public class BookRestController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public BookRestController(BookService bookService,
                              CommentService commentService) {
        this.bookService     = bookService;
        this.commentService  = commentService;
    }

   @GetMapping({"/","/index"})
    public String listPage(Model model) {
        List<Book> books = bookService.findAllBook();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book")
    public String bookPage(@RequestParam("id") Long id, Model model) {
        Book book = bookService.selectBookByID(id);
        model.addAttribute("book", book);

        List<Comment> comments = commentService.selectCommentByBookID(id);
        model.addAttribute("comments", comments);

        return "book";
    }

    @GetMapping("/index/selectby")
    public String bookSelectBy(@RequestParam("FindText") String findText,
                               @RequestParam("cbFindType") String findType,
                                Model model) {
        List<Book> books;
        switch (findType) {
            case "name":
                books = bookService.findAllBookByName(findText);
                break;
            case "author":
                books = bookService.findAllBookByAuthor(findText);
                break;
            case "genre":
                books = bookService.findAllBookByGenre(findText);
                break;
            default:
                books = bookService.findAllBook();
                break;
        }
        model.addAttribute("books", books);
        return "index";
    }

    @PostMapping("/index/selectinset")
    public String bookInsetSelect(Model model) {
        Book book = Book.builder().id(0L).build();
        model.addAttribute("book", book);
        return "bookInsert";
    }

    @PostMapping("/book/insert")
    public String bookInset(@RequestParam("BookName") String bookName,
                            @RequestParam("Author") String authortxt,
                            @RequestParam("Genre") String genretxt,
                            Model model) {

        String returnTmp;
        Book newBook = bookService.insertBook(bookName, authortxt, genretxt);

        if (newBook !=null) {
            returnTmp = "redirect:/";
        } else {
            newBook = new Book();
            returnTmp = "bookInsert";
        }

        model.addAttribute("book", newBook);
        return returnTmp;
    }

    @PostMapping("/book/update")
    public String bookUpdate(@RequestParam("id") Long id,
                             @RequestParam("BookName") String bookName,
                             @RequestParam("Author") String authortxt,
                             @RequestParam("Genre") String genretxt,
                             Model model) {

        bookService.updateBook(id, bookName, authortxt, genretxt);
        return bookPage(id, model);
    }
    @PostMapping("/book/delete")
    public String bookDelete(@RequestParam("id") Long id, Model model) {
        bookService.deleteBook(id);
        List<Book> books = bookService.findAllBook();
        model.addAttribute("books", books);
        return "redirect:/";
    }

    @PostMapping("/comment/insert")
    public String commentInsert(@RequestParam("bookid") Long bookid,@RequestParam("comment") String comment, Model model) {
        commentService.insertComment(bookid, comment);
        return bookPage(bookid, model);
    }

    @PostMapping("/comment/delete")
    public String commentDelete(@RequestParam("id") Long id, @RequestParam("bookid") Long bookid, Model model) {
        commentService.deleteComment(id);
        return bookPage(bookid, model);
    }

    @ExceptionHandler()
    public ModelAndView handleNPE(NullPointerException e) {
        ModelAndView modelAndView = new ModelAndView("err500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

}

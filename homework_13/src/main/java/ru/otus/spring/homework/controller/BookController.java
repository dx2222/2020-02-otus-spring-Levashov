package ru.otus.spring.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.dto.*;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    private JdbcMutableAclService aclService;

    @Autowired
    public BookController(BookService bookService,
                          CommentService commentService) {
        this.bookService     = bookService;
        this.commentService  = commentService;
    }

    private boolean checkRigth(Acl acl) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final List<Permission> permissions =  Arrays.asList(BasePermission.WRITE, BasePermission.ADMINISTRATION);

        final List<Sid> sids = new ArrayList<>();
        userDetails.getAuthorities().forEach(role -> sids.add(new GrantedAuthoritySid(role.toString())));

        sids.add(new GrantedAuthoritySid(userDetails.getUsername()));

        boolean result = false;

        try {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN))
                    || acl.getOwner().equals(new PrincipalSid(userDetails.getUsername()))
                    || acl.isGranted(permissions, sids, false)
            ) {
                result = true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return  result;
    }


    private boolean checkRigthBook(Long id) {
        return  checkRigth(aclService.readAclById(new ObjectIdentityImpl(Book.class, id)));
    }


   @GetMapping({"/","/index"})
    public String listPage(Model model) {
        List<BookDto> books = bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
        model.addAttribute("books", books);

       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       String strIndex = "index";

       if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN))) {
           strIndex = "indexAdmin";
       }
        return strIndex;
    }

    @GetMapping("/book")
    public String bookPage(@RequestParam("id") Long id, Model model) {
        BookDto book = BookDto.toDto(bookService.selectBookByID(id));
        model.addAttribute("book", book);

        List<CommentDto> comments = commentService.selectCommentByBookID(id).stream().map(CommentDto::toDto).collect(Collectors.toList());
        model.addAttribute("comments", comments);

        String strBook = "bookUser";

        if (checkRigthBook(id)) {
                strBook = "book";
        }

        return strBook;
    }

    @GetMapping("/index/selectby")
    public String bookSelectBy(@RequestParam("FindText") String findText,
                               @RequestParam("cbFindType") String findType,
                                Model model) {
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
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/index/selectinset")
    public String bookInsetSelect(Model model) {
        BookDto book = BookDto.builder().id(0L).build();
        model.addAttribute("book", book);
        return "bookInsert";
    }

    @PostMapping("/book/insert")
    public String bookInset(@RequestParam("BookName") String bookName,
                            @RequestParam("Author") String authortxt,
                            @RequestParam("Genre") String genretxt,
                            Model model) {

        String returnTmp = "redirect:/";
        BookDto newBook = BookDto.builder().id(0L).build();
        try {
           newBook = BookDto.toDto(bookService.insertBook(bookName, authortxt, genretxt));

           if (newBook !=null) {
                returnTmp = "redirect:/";
           } else {

                returnTmp = "bookInsert";
           }
        } catch (Exception ignored) {

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

        if (checkRigthBook(id)) {
            bookService.updateBook(id, bookName, authortxt, genretxt);
        }

        return bookPage(id, model);
    }
    @PostMapping("/book/delete")
    public String bookDelete(@RequestParam("id") Long id, Model model) {

        if (checkRigthBook(id)) {
            bookService.deleteBook(id);
        }
        List<BookDto> books = bookService.findAllBook().stream().map(BookDto::toDto).collect(Collectors.toList());
        model.addAttribute("books", books);
        return "redirect:/";
    }

    @PostMapping("/comment/insert")
    public String commentInsert(@RequestParam("bookid") Long bookid,@RequestParam("comment") String comment, Model model) {
        try {
            commentService.insertComment(bookid, comment);
        } catch (Exception ignored) {

        }
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

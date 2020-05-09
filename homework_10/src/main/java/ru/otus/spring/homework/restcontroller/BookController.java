package ru.otus.spring.homework.restcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

   @GetMapping({"/","/index"})
    public String listPage(Model model) {
        return "index";
    }
}

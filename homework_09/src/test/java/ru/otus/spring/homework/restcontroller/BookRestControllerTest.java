package ru.otus.spring.homework.restcontroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.service.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BookRestControllerTest {
    @Autowired
    private BookRestController controller;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    @Test
    public void listPageTest() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

}

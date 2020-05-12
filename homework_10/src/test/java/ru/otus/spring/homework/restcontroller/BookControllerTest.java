package ru.otus.spring.homework.restcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("BookController")
public class BookControllerTest {
    @Autowired
    private BookController controller;

    @Autowired
    private MockMvc mvc;

    @Test
    public void indexPageTest() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

}

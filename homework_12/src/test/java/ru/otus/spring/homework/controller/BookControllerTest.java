package ru.otus.spring.homework.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.repository.UserRepositoryJpa;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BookControllerTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private BookController controller;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listPageTest() throws Exception {
        mockMvc.perform(get("/index")).andExpect(status().is(302));
    }

  @WithMockUser(
          username = "admin1",
          password = "654321",
          authorities = {"ADMIN"}
  )
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        mockMvc.perform(get("/index")).andExpect(status().isOk());
    }


}

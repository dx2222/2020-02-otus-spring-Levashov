package ru.otus.spring.homework.restcontroller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestControllerTest {

    @Autowired
    private RouterFunction route;

    @Test
    @DisplayName("список всех книг")
    public void testRouteBook() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/book")
                .exchange()
                .expectStatus()
                .isOk();

    }
}
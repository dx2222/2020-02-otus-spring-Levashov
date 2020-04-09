package ru.otus.spring.homework_03.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Класс ConsoleServiceImpl")
class StudentTest {

    private static final String SURNAME   = "1111";
    private static final String FIRSTNAME = "2222";

    @Test
    @DisplayName("studentTest")
    public void studentTest() {
        Student student = new Student(SURNAME,  FIRSTNAME);

        assertThat(student)
                .hasFieldOrPropertyWithValue("surName", SURNAME)
                .hasFieldOrPropertyWithValue("firstName", FIRSTNAME);
    }

}
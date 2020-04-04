package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Класс ConsoleServiceImpl")
class StudentTest {

    @Test
    @DisplayName("studentTest")
    public void studentTest() {
        Student student = new Student("1111",  "2222");

        assertThat(student)
                .hasFieldOrPropertyWithValue("surName", "1111")
                .hasFieldOrPropertyWithValue("firstName", "2222");

    }

}
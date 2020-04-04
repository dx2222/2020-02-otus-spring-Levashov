package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Question")
class QuestionTest {

    @Test
    @DisplayName("questionTest")
    public void questionTest() {
        try {
            String[] possibleAnswer = {"2222","3333"};

            Question question = new Question("1111", possibleAnswer, "4444");

            assertThat(question)
                    .hasFieldOrPropertyWithValue("question", "1111")
                    .hasFieldOrPropertyWithValue("possibleAnswer", possibleAnswer)
                    .hasFieldOrPropertyWithValue("rightAnswer", "4444");
        } catch (Exception e) {
            System.out.println("QuestionTest error " + e.getMessage());
        }
    }

}
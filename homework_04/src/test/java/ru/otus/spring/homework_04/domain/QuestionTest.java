package ru.otus.spring.homework_04.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Класс Question")
class QuestionTest {

    private static final String QUESTION   = "1111";
    private static final String[] POSSIBLEANSWER   = {"2222", "3333"};
    private static final String RIGHTANSWER   = "4444";

    @Test
    @DisplayName("questionTest")
    public void questionTest() {
        try {

            Question question = new Question(QUESTION, POSSIBLEANSWER, RIGHTANSWER);

            assertThat(question)
                    .hasFieldOrPropertyWithValue("question", QUESTION)
                    .hasFieldOrPropertyWithValue("possibleAnswer", POSSIBLEANSWER)
                    .hasFieldOrPropertyWithValue("rightAnswer", RIGHTANSWER);
        } catch (Exception e) {
            System.out.println("QuestionTest error " + e.getMessage());
        }
    }

}
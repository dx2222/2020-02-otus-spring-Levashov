package ru.otus.spring.homework_03.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.MessageSource;
import ru.otus.spring.homework_03.domain.Student;
import ru.otus.spring.homework_03.domain.Question;
import org.mockito.Mockito;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("Класс ConsoleServiceImpl")
class ConsoleServiceImplTest {

    private static final String SURNAME   = "1111";
    private static final String FIRSTNAME = "2222";

    private static final String QUESTION   = "1111";
    private static final String[] POSSIBLEANSWER   = {"2222", "3333"};
    private static final String RIGHTANSWER   = "4444";

    @MockBean
    private MessageSource msMock;

    @MockBean
    private IOService ioMock;

    @Autowired
    private ConsoleServiceImpl console;

    @Test
    @DisplayName("askNameTest")
    public void askNameTest() {

        Mockito.when(ioMock.readString()).thenReturn(SURNAME).thenReturn(FIRSTNAME);

        Mockito.doNothing().when(ioMock).printOut(Mockito.anyString());
        Mockito.doNothing().when(ioMock).printOutLn(Mockito.anyString());

        Student student = console.askName();

        assertThat(student)
                .hasFieldOrPropertyWithValue("surName", SURNAME)
                .hasFieldOrPropertyWithValue("firstName", FIRSTNAME);
    }

    @Test
    @DisplayName("askQuestionTest")
    public void askQuestionTest() {

        Mockito.when(ioMock.readString()).thenReturn(RIGHTANSWER);

        Mockito.doNothing().when(ioMock).printOut(Mockito.anyString());
        Mockito.doNothing().when(ioMock).printOutLn(Mockito.anyString());

        Mockito.when(msMock.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any(Locale.class))).thenReturn(QUESTION);
        boolean res = false;
        try {

            Question question = new Question(QUESTION, POSSIBLEANSWER, RIGHTANSWER);

            res = console.askQuestion(question,1);
        } catch (Exception e) {
            System.out.println("QuestionTest error " + e.getMessage());
        }

        assertThat(res);
    }


}
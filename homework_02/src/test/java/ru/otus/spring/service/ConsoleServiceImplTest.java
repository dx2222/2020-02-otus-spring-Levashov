package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Question;
import org.mockito.Mockito;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("Класс ConsoleServiceImpl")
class ConsoleServiceImplTest {


    @Test
    @DisplayName("askNameTest")
    public void askNameTest() {
        IOService ioMock = mock(IOService.class);
        MessageSource msMock = mock(MessageSource.class);
        ConsoleService console = new ConsoleServiceImpl(ioMock, msMock);

        Mockito.when(ioMock.readString()).thenReturn("1111").thenReturn("2222");

        Mockito.doNothing().when(ioMock).printOut(Mockito.anyString());
        Mockito.doNothing().when(ioMock).printOutLn(Mockito.anyString());

        Student student = console.askName();

        assertThat(student)
                .hasFieldOrPropertyWithValue("surName", "1111")
                .hasFieldOrPropertyWithValue("firstName", "2222");

    }

    @Test
    @DisplayName("askQuestionTest")
    public void askQuestionTest() {
        IOService ioMock = mock(IOService.class);
        MessageSource msMock = mock(MessageSource.class);

        ConsoleService console = new ConsoleServiceImpl(ioMock, msMock);

        Mockito.when(ioMock.readString()).thenReturn("1111");

        Mockito.doNothing().when(ioMock).printOut(Mockito.anyString());
        Mockito.doNothing().when(ioMock).printOutLn(Mockito.anyString());

        Mockito.when(msMock.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any(Locale.class))).thenReturn("Вопрос");
        boolean res = false;
        try {
            String[] possibleAnswer = {"2222", "3333"};

            Question question = new Question("1111", possibleAnswer, "4444");

            res = console.askQuestion(question,1);
        } catch (Exception e) {
            System.out.println("QuestionTest error " + e.getMessage());
        }

        assertThat(res);
    }


}
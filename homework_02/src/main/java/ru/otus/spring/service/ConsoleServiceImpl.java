package ru.otus.spring.service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import java.io.PrintStream;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final MessageSource messageSource;

    private final IOService ioService;

    @Value("${lang.locale}")
    private Locale locale;

    @Value("${number.correct.answers}")
    private int numberCorrectAnswers;

    @Autowired
    public ConsoleServiceImpl(IOService ioService, MessageSource messageSource) {
        this.ioService = ioService;
        this.messageSource = messageSource;
    }


    public Student askName() {

        ioService.printOut(messageSource.getMessage("exam.surname",null, locale ));
        String surName = ioService.readString();

        ioService.printOut(messageSource.getMessage("exam.firstname",null, locale ));
        String firstName = ioService.readString();

        return new Student(surName, firstName);
    }

    public boolean askQuestion( Question question, int number) throws UnsupportedEncodingException {

        ioService.printOutLn(messageSource.getMessage("exam.question",new String [] {String.valueOf(number)}, locale ) + question.getQuestion()+"?");

        int i = 0;
        for (String curAnswer : question.getPossibleAnswer()) {
            i++;
            if (!curAnswer.equals("")) {
                ioService.printOutLn(i + " - " + curAnswer);
            }
        }
        ioService.printOut(messageSource.getMessage("exam.answer",null, locale ));

        return question.getRightAnswer().trim().toLowerCase().equals(ioService.readString().trim().toLowerCase());
    }

    public void printResult(int testResult) {

        ioService.printOutLn(messageSource.getMessage("exam.result",null, locale )+testResult);
        if (testResult >= numberCorrectAnswers) {
            ioService.printOutLn(messageSource.getMessage("test.passed.successfully",null, locale ));
        } else {
            ioService.printOutLn(messageSource.getMessage("test.failed",null, locale ));
        }
    }

}

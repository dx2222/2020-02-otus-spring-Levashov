package ru.otus.spring.homework_03.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework_03.config.ApplicationSettings;
import ru.otus.spring.homework_03.domain.Question;
import ru.otus.spring.homework_03.domain.Student;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final MessageSource messageSource;

    private final IOService ioService;

    @Autowired
    private ApplicationSettings settings;

    @Autowired
    public ConsoleServiceImpl(IOService ioService, MessageSource messageSource) {
        this.ioService = ioService;
        this.messageSource = messageSource;
    }


    public Student askName() {

        ioService.printOut(messageSource.getMessage("exam.surname",null, settings.getLocale() ));
        String surName = ioService.readString();

        ioService.printOut(messageSource.getMessage("exam.firstname",null, settings.getLocale() ));
        String firstName = ioService.readString();

        return new Student(surName, firstName);
    }

    public boolean askQuestion( Question question, int number) throws UnsupportedEncodingException {

        ioService.printOutLn(messageSource.getMessage("exam.question",new String [] {String.valueOf(number)}, settings.getLocale() ) + question.getQuestion()+"?");

        int i = 0;
        for (String curAnswer : question.getPossibleAnswer()) {
            i++;
            if (!curAnswer.equals("")) {
                ioService.printOutLn(i + " - " + curAnswer);
            }
        }
        ioService.printOut(messageSource.getMessage("exam.answer",null, settings.getLocale() ));

        return question.getRightAnswer().trim().toLowerCase().equals(ioService.readString().trim().toLowerCase());
    }

    public void printResult(int testResult) {

        ioService.printOutLn(messageSource.getMessage("exam.result",null, settings.getLocale() )+testResult);
        if (testResult >= settings.getNumberCorrectAnswers()) {
            ioService.printOutLn(messageSource.getMessage("test.passed.successfully",null, settings.getLocale() ));
        } else {
            ioService.printOutLn(messageSource.getMessage("test.failed",null, settings.getLocale() ));
        }
    }

}

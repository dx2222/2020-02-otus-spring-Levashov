package ru.otus.spring.homework_03.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework_03.config.ApplicationSettings;
import ru.otus.spring.homework_03.domain.Question;
import ru.otus.spring.homework_03.domain.Student;
import ru.otus.spring.homework_03.domain.TxtConst;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final MessageService messageService;

    private final IOService ioService;

    @Autowired
    private ApplicationSettings settings;

    @Autowired
    public ConsoleServiceImpl(IOService ioService, MessageService messageService) {
        this.ioService       = ioService;
        this.messageService = messageService;
    }

    public Student askName() {

        ioService.printOut(messageService.getMessage(TxtConst.EXAM_SURNAME));
        String surName = ioService.readString();

        ioService.printOut(messageService.getMessage(TxtConst.EXAM_FIRSTNAME ));
        String firstName = ioService.readString();

        return new Student(surName, firstName);
    }

    public boolean askQuestion( Question question, int number) throws UnsupportedEncodingException {

        ioService.printOutLn(messageService.getMessage(TxtConst.EXAM_QUESTION,new String [] {String.valueOf(number)}) + question.getQuestion()+"?");

        int i = 0;
        for (String curAnswer : question.getPossibleAnswer()) {
            i++;
            if (!curAnswer.equals("")) {
                ioService.printOutLn(i + " - " + curAnswer);
            }
        }
        ioService.printOut(messageService.getMessage(TxtConst.EXAM_ANSWER ));

        return question.getRightAnswer().trim().toLowerCase().equals(ioService.readString().trim().toLowerCase());
    }

    public void printResult(int testResult) {

        ioService.printOutLn(messageService.getMessage(TxtConst.EXAM_RESULT)+testResult);
        if (testResult >= settings.getNumberCorrectAnswers()) {
            ioService.printOutLn(messageService.getMessage(TxtConst.TEST_PASSED_SUCCESSFULLY));
        } else {
            ioService.printOutLn(messageService.getMessage(TxtConst.TEST_FAILED));
        }
    }

}

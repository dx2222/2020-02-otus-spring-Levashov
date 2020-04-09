package ru.otus.spring.homework_03.service;

import ru.otus.spring.homework_03.domain.Question;
import ru.otus.spring.homework_03.domain.Student;

import java.io.UnsupportedEncodingException;

public interface ConsoleService {

    Student askName();

    boolean askQuestion(Question question, int number) throws UnsupportedEncodingException;

    void printResult(int testResult) ;
}

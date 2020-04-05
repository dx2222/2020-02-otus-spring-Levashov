package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

import java.io.UnsupportedEncodingException;

public interface ConsoleService {

    Student askName();

    boolean askQuestion(Question question, int number) throws UnsupportedEncodingException;

    void printResult(int testResult) ;
}

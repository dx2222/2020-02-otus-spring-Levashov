package ru.otus.spring.homework_04.service;

import ru.otus.spring.homework_04.domain.Question;

public interface QuestionService {

    Question getByNumber(int number);

    int getCountQuestion();
}

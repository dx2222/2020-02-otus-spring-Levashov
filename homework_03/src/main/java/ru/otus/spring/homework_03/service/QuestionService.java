package ru.otus.spring.homework_03.service;

import ru.otus.spring.homework_03.domain.Question;

public interface QuestionService {

    Question getByNumber(int number);

    int getCountQuestion();
}

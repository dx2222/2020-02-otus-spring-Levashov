package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface QuestionService {

    Question getByNumber(int number);

    int getCountQuestion();
}

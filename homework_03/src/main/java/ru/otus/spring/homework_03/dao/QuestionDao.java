package ru.otus.spring.homework_03.dao;

import ru.otus.spring.homework_03.domain.Question;

public interface QuestionDao {

    Question findByNumber(int number);

    int getCountQuestion();
}

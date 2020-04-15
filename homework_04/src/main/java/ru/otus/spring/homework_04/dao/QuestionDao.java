package ru.otus.spring.homework_04.dao;

import ru.otus.spring.homework_04.domain.Question;

public interface QuestionDao {

    Question findByNumber(int number);

    int getCountQuestion();
}

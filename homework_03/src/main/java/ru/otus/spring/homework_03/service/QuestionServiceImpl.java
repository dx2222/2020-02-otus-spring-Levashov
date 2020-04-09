package ru.otus.spring.homework_03.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework_03.dao.QuestionDao;
import ru.otus.spring.homework_03.domain.Question;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    @Autowired
    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public Question getByNumber(int number) {
        return dao.findByNumber(number);
    }

    public int getCountQuestion() {
        return dao.getCountQuestion();
    }
}

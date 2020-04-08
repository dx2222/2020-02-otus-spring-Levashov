package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

import org.springframework.beans.factory.annotation.Value;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final QuestionService questionService;
    private final ConsoleService console;

    @Autowired
    public ExaminationServiceImpl(QuestionService questionService1, ConsoleService console1) {
        this.questionService = questionService1;
        this.console = console1;
    }

    public void startTesting() {

        try {
            Student student = console.askName();
            int testResult = 0;
            for (int i = 0; i < questionService.getCountQuestion(); i++) {
                if (console.askQuestion(questionService.getByNumber(i), i + 1)) {
                    testResult++;
                }
            }
            console.printResult(testResult);

        } catch(Exception e){
            System.out.println("startTesting ERROR: " + e.getMessage());
        }
    }
}

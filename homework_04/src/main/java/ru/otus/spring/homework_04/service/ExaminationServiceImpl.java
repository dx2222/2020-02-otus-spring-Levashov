package ru.otus.spring.homework_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework_04.config.ApplicationSettings;
import ru.otus.spring.homework_04.domain.Student;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final QuestionService questionService;
    private final ConsoleService console;

    private Student student;
    private int testResult;

    @Autowired
    private ApplicationSettings settings;

    @Autowired
    public ExaminationServiceImpl(QuestionService questionService1, ConsoleService console1) {
        this.questionService = questionService1;
        this.console = console1;
    }

    public void askName() {
        try {
            student = console.askName();
        } catch(Exception e){
            System.out.println("askName ERROR: " + e.getMessage());
        }
    }

    public void askQuestions() {
        try {
            testResult = 0;
            for (int i = 0; i < questionService.getCountQuestion(); i++) {
                if (console.askQuestion(questionService.getByNumber(i), i + 1)) {
                    testResult++;
                }
            }
        } catch(Exception e){
            System.out.println("askQuestions ERROR: " + e.getMessage());
        }
    }

    public void printResult() {
        try {
            boolean successfully = testResult >= settings.getNumberCorrectAnswers();
            console.printResult(testResult, successfully);
        } catch(Exception e){
            System.out.println("printResult ERROR: " + e.getMessage());
        }
    }

    public void startTesting() {
      askName();
      askQuestions();
      printResult();
    }
}

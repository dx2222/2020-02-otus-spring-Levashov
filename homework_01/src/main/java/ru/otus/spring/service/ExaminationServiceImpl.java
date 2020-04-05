package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public class ExaminationServiceImpl implements ExaminationService {

    private Student student;
    private QuestionService questionService;
    private ConsoleService console;

    public ExaminationServiceImpl() {
    }

    public ExaminationServiceImpl(QuestionService questionService1, ConsoleService console1, Student student1) {
        this.student = student1;
        this.questionService = questionService1;
        this.console = console1;
    }

    public void startTesting() {

        try {
            this.student = console.askName();
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

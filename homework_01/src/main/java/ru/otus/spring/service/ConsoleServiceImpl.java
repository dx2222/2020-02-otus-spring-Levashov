package ru.otus.spring.service;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import java.io.PrintStream;

public class ConsoleServiceImpl implements ConsoleService {

    private Scanner scanner;

    public ConsoleServiceImpl(Scanner scanner){
        this.scanner = scanner;
    }

    public ConsoleServiceImpl(){
        this.scanner = new Scanner(System.in);
    }

    public Student askName() {
        System.out.print("Введите Имя: ");
        String surName = scanner.nextLine();
        System.out.print("Введите Фамилию: ");
        String firstName = scanner.nextLine();

        return new Student(surName, firstName);
    }

    public boolean askQuestion( Question question, int number) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        System.out.println("Вопрос №" + number+": "+question.getQuestion()+"?");

        int i = 0;
        for (String curAnswer : question.getPossibleAnswer()) {
            i++;
            if (!curAnswer.equals("")) {
                System.out.println(i + " - " + curAnswer);
            }
        }
        System.out.print("Ответ: ");

        return question.getRightAnswer().trim().toLowerCase().equals(scanner.next().trim().toLowerCase());
    }

    public void printResult(int testResult) {
        System.out.println("Количество правильных ответов: "+testResult);
    }

}

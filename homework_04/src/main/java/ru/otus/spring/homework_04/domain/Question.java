package ru.otus.spring.homework_04.domain;

import java.io.IOException;
import lombok.Data;

@Data
public class Question {

    private final String question;
    private final String[] possibleAnswer;
    private final String rightAnswer;

    public Question(String question, String[] possibleAnswer, String rightAnswer) throws  IOException {
        if ((question == null)|(possibleAnswer == null)|(rightAnswer == null)){
            throw new IOException();
        } else {
            this.question = question;
            this.possibleAnswer = possibleAnswer;
            this.rightAnswer = rightAnswer;
        }
    }

}

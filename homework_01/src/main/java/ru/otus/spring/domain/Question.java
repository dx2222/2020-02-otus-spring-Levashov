package ru.otus.spring.domain;

import java.io.IOException;

public class Question {

    private final String question;
    private final String[] possibleAnswer;
    private final String rightAnswer;

    public String getQuestion() {
            return question;
    }

    public String[] getPossibleAnswer() {
        return possibleAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

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

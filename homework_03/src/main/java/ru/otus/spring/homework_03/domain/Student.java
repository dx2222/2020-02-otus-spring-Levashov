package ru.otus.spring.homework_03.domain;

import lombok.Data;

@Data
public class Student {

    private String surName;
    private String firstName ;

    public Student(String surName, String firstName) {
        this.surName   = surName;
        this.firstName = firstName;
    }

}

package ru.otus.spring.domain;

public class Student {

    private  String surName;
    private  String firstName ;

    private Student() {
    }

    public Student(String surName, String firstName) {
        this.surName   = surName;
        this.firstName = firstName;
    }

}

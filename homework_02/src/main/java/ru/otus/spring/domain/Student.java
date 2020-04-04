package ru.otus.spring.domain;

public class Student {

    private String surName;
    private String firstName ;

    public String getSurName() {
         return surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Student(String surName, String firstName) {
        this.surName   = surName;
        this.firstName = firstName;
    }

}

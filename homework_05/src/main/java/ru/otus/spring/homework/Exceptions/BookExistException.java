package ru.otus.spring.homework.Exceptions;

public class BookExistException extends Exception {

    public BookExistException(String message) {
        super(message);
    }
}

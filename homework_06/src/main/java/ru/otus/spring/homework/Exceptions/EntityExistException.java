package ru.otus.spring.homework.Exceptions;

public class EntityExistException extends Exception {

    public EntityExistException(String message) {
        super(message);
    }
}

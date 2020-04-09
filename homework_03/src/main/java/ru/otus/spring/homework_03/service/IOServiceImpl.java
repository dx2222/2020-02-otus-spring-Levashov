package ru.otus.spring.homework_03.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner;

    public IOServiceImpl(){
        this.scanner = new Scanner(System.in);
    }

    public void printOut(String st) {
      System.out.print(st);
    }

    public void printOutLn(String st) {
        System.out.println(st);
    }

    public String readString() {
        return scanner.nextLine();
    }

}

package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

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

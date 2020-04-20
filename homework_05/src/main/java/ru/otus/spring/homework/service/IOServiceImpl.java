package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream printStream;

    @Autowired
    public IOServiceImpl(){
        this.scanner     = new Scanner(System.in);
        this.printStream = new PrintStream(System.out);
    }

    public IOServiceImpl( Scanner scanner, PrintStream printStream){
        this.scanner     = scanner;
        this.printStream = printStream;
    }

    public void printOut(String st) {
        printStream.print(st);
    }

    public void printOutLn(String st) {
        printStream.println(st);
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Long readLong() {
        if (scanner.hasNextLong()) {
            return Long.valueOf( scanner.nextLine());
        } else {
            return 0L;
        }
    }

}

package ru.otus.spring.homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebBookBaseApplication {


	public static void main(String[] args) throws Exception {
		//Console.main(args);
		ApplicationContext context = SpringApplication.run(WebBookBaseApplication.class, args);
	}
}

package ru.otus.spring.homework_03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ru.otus.spring.homework_03.service.ExaminationService;

@SpringBootApplication
public class Homework03Application {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Homework03Application.class, args);

		ExaminationService examination = context.getBean(ExaminationService.class);

		examination.startTesting();
	}

}

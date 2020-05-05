package ru.otus.spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.h2.tools.Console;

@SpringBootApplication
@EnableConfigurationProperties
public class BookBaseApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(BookBaseApplication.class, args);
	}
}

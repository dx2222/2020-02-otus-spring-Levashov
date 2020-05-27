package ru.otus.spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class WebFluxBookApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(WebFluxBookApplication.class, args);
	}

}

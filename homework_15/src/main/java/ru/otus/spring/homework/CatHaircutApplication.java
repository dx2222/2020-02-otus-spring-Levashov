package ru.otus.spring.homework;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.spring.homework.domain.Cat;
import ru.otus.spring.homework.messagingGateway.Grooming;

import java.util.UUID;

@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates"})
@ComponentScan
@Configuration
@EnableIntegration
public class CatHaircutApplication {



	public static void main(String[] args) throws Exception {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(CatHaircutApplication.class);

		Grooming grooming = context.getBean(Grooming.class);

		while (true)
		{
			Cat cat = Cat.builder().name("Вася").location("Дом").coatLength(RandomUtils.nextDouble(1, 10)).uuid(UUID.randomUUID().toString()).build();
			System.out.println("Стрижка кота");
			System.out.println(cat);
			Cat cat2 = grooming.process(cat);
			System.out.println("Стрижка завершина!!!");
			System.out.println(cat2);
		}

	}
}

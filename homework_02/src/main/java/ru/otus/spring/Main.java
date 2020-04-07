package ru.otus.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.service.ExaminationService;
import ru.otus.spring.config.*;

@Import(MessageSourceConfig.class)
@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ExaminationService examination = context.getBean(ExaminationService.class);

        examination.startTesting();
    }
}

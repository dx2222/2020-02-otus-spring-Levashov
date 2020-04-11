package ru.otus.spring.homework_03.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApplicationConfig {

    @Bean
    MessageSource messageSource(MessageSourceSettings settings) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(settings.getBasename());
        messageSource.setDefaultEncoding(settings.getDefaultEncoding());
        return messageSource;
    }

}

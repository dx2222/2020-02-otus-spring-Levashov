package ru.otus.spring.homework_04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("application")
@Component
public class ApplicationSettings {

    private String fileName;
    private Locale locale;
    private Integer numberCorrectAnswers;

}
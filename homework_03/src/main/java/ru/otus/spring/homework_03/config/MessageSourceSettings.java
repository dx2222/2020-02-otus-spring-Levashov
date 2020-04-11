package ru.otus.spring.homework_03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("messagesource")
@Component
public class MessageSourceSettings {

    private String basename;
    private String defaultEncoding;

}
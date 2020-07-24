package ru.otus.spring.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("groomer")
@Component
public class GroomerSettings {

    private String name = "Специалист";
    private Double minCoatLength = 1.0;
    private Double maxDecCoatLength = 5.0;
    private String location = "Груминг-студия";
}
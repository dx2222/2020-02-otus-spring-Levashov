package ru.otus.spring.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("location")
@Component
public class LocationSettings {

    private String LOCATION_HOME = "Дом";
    private String LOCATION_GROOMING_STUDIO = "Груминг-студия";

}
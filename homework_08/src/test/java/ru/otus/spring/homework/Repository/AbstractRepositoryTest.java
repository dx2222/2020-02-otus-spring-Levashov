package ru.otus.spring.homework.Repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.homework.config", "ru.otus.spring.homework.repository"})
public abstract class AbstractRepositoryTest {
}

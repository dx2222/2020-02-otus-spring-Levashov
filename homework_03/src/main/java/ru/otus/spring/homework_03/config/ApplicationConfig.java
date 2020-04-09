package ru.otus.spring.homework_03.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.homework_03.dao.QuestionDaoSimple;

@Configuration
public class ApplicationConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    QuestionDaoSimple questionDaoSimple(ApplicationSettings settings) {
        return new QuestionDaoSimple(settings.getFileName(), settings.getLocale());
    }

}

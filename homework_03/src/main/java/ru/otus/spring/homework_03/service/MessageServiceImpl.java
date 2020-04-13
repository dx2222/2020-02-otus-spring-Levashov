package ru.otus.spring.homework_03.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework_03.config.ApplicationSettings;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    @Autowired
    private ApplicationSettings settings;

    public MessageServiceImpl(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String getMessage(String var1) {
        return messageSource.getMessage(var1,null, settings.getLocale());
    }

    public String getMessage(String var1, Object[] var2) {
        return messageSource.getMessage(var1,var2, settings.getLocale());
    }
}

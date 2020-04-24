package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.config.ApplicationSettings;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final ApplicationSettings settings;

    @Autowired
    public MessageServiceImpl(MessageSource messageSource, ApplicationSettings settings){
        this.messageSource = messageSource;
        this.settings      = settings;
    }

    public String getMessage(String var1) {
        return messageSource.getMessage(var1,null, settings.getLocale());
    }

    public String getMessage(String var1, Object[] var2) {
        return messageSource.getMessage(var1,var2, settings.getLocale());
    }
}

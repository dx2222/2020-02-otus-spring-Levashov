package ru.otus.spring.homework.messagingGateway;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.homework.domain.Cat;

@MessagingGateway
public interface Grooming {

    @Gateway(requestChannel = "CatInChannel", replyChannel = "CatOutChannel")
    Cat process(Cat cat);
}

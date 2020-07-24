package ru.otus.spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
@IntegrationComponentScan
public class AppConfiguration {
    @Bean
    public QueueChannel CatInChannel() {
        return MessageChannels.queue(10).get();
    }
    @Bean
    public PublishSubscribeChannel CatOutChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow GroomingFlow() {
        return IntegrationFlows.from("CatInChannel")

                .handle("carryingService", "toCarrying")
                .handle("transportService", "transporting")
                .handle("carryingService", "getCat")
                .handle("groomingService", "grooming")
                .handle("carryingService", "toCarrying")
                .handle("transportService", "transporting")
                .handle("carryingService", "getCat")
                .channel("CatOutChannel")
                .get();
    }
}

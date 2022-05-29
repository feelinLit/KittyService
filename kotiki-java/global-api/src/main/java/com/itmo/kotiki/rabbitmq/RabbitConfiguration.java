package com.itmo.kotiki.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public DirectExchange personExchange() {
        return new DirectExchange("person.rpc.exchange");
    }

    @Bean
    public DirectExchange kittyExchange() {
        return new DirectExchange("kitty.rpc.exchange");
    }
}
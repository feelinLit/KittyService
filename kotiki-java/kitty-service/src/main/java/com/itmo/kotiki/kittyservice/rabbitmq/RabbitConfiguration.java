package com.itmo.kotiki.kittyservice.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue queueFindAll() {
        return new Queue("kitty.rpc.requests.findAll");
    }

    @Bean
    public Queue queueFindAllByColor() {
        return new Queue("kitty.rpc.requests.findAllByColor");
    }

    @Bean
    public Queue queueFindAllByBreed() {
        return new Queue("kitty.rpc.requests.findAllByBreed");
    }

    @Bean
    public Queue queueFindById() {
        return new Queue("kitty.rpc.requests.findById");
    }

    @Bean
    public Queue queueSave() {
        return new Queue("kitty.rpc.requests.save");
    }

    @Bean
    public Queue queueSaveOrUpdate() {
        return new Queue("kitty.rpc.requests.saveOrUpdate");
    }

    @Bean
    public Queue queueDelete() {
        return new Queue("kitty.rpc.requests.delete");
    }

    @Bean
    public Queue queueAddFriend() {
        return new Queue("kitty.rpc.requests.addFriend");
    }

    @Bean
    public DirectExchange kittyExchange() {
        return new DirectExchange("kitty.rpc.exchange");
    }

    @Bean
    public Binding bindingKittyFindAll(DirectExchange kittyExchange,
                                       Queue queueFindAll) {
        return BindingBuilder.bind(queueFindAll)
                .to(kittyExchange)
                .with("findAll");
    }

    @Bean
    public Binding bindingKittyFindAllByColor(DirectExchange kittyExchange,
                                              Queue queueFindAllByColor) {
        return BindingBuilder.bind(queueFindAllByColor)
                .to(kittyExchange)
                .with("findAllByColor");
    }

    @Bean
    public Binding bindingKittyFindAllByBreed(DirectExchange kittyExchange,
                                              Queue queueFindAllByBreed) {
        return BindingBuilder.bind(queueFindAllByBreed)
                .to(kittyExchange)
                .with("findAllByBreed");
    }

    @Bean
    public Binding bindingKittyFindById(DirectExchange kittyExchange,
                                        Queue queueFindById) {
        return BindingBuilder.bind(queueFindById)
                .to(kittyExchange)
                .with("findById");
    }

    @Bean
    public Binding bindingKittySave(DirectExchange kittyExchange,
                                    Queue queueSave) {
        return BindingBuilder.bind(queueSave)
                .to(kittyExchange)
                .with("save");
    }

    @Bean
    public Binding bindingKittySaveOrUpdate(DirectExchange kittyExchange,
                                            Queue queueSaveOrUpdate) {
        return BindingBuilder.bind(queueSaveOrUpdate)
                .to(kittyExchange)
                .with("saveOrUpdate");
    }

    @Bean
    public Binding bindingKittyDelete(DirectExchange kittyExchange,
                                      Queue queueDelete) {
        return BindingBuilder.bind(queueDelete)
                .to(kittyExchange)
                .with("delete");
    }

    @Bean
    public Binding bindingKittyAddFriend(DirectExchange kittyExchange,
                                         Queue queueAddFriend) {
        return BindingBuilder.bind(queueAddFriend)
                .to(kittyExchange)
                .with("addFriend");
    }
}

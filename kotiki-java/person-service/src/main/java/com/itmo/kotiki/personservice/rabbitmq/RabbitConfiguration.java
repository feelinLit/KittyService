package com.itmo.kotiki.personservice.rabbitmq;

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
        return new Queue("person.rpc.requests.findAll", false);
    }

    @Bean
    public Queue queueFindAllByName() {
        return new Queue("person.rpc.requests.findAllByName", false);
    }

    @Bean
    public Queue queueFindById() {
        return new Queue("person.rpc.requests.findById", false);
    }

    @Bean
    public Queue queueFindByUsername() {
        return new Queue("person.rpc.requests.findByUsername", false);
    }

    @Bean
    public Queue queueSave() {
        return new Queue("person.rpc.requests.save", false);
    }

    @Bean
    public Queue queueSaveOrUpdate() {
        return new Queue("person.rpc.requests.saveOrUpdate", false);
    }

    @Bean
    public Queue queueDelete() {
        return new Queue("person.rpc.requests.delete", false);
    }

    @Bean
    public Queue queueAddKitty() {
        return new Queue("person.rpc.requests.addKitty", false);
    }

    @Bean
    public DirectExchange personExchange() {
        return new DirectExchange("person.rpc.exchange");
    }

    @Bean
    public Binding bindingPersonFindAll(DirectExchange exchange,
                                        Queue queueFindAll) {
        return BindingBuilder.bind(queueFindAll)
                .to(exchange)
                .with("findAll");
    }

    @Bean
    public Binding bindingPersonFindAllByName(DirectExchange exchange,
                                              Queue queueFindAllByName) {
        return BindingBuilder.bind(queueFindAllByName)
                .to(exchange)
                .with("findAllByName");
    }

    @Bean
    public Binding bindingPersonFindById(DirectExchange exchange,
                                         Queue queueFindById) {
        return BindingBuilder.bind(queueFindById)
                .to(exchange)
                .with("findById");
    }

    @Bean
    public Binding bindingPersonFindByUsername(DirectExchange exchange,
                                               Queue queueFindByUsername) {
        return BindingBuilder.bind(queueFindByUsername)
                .to(exchange)
                .with("findByUsername");
    }

    @Bean
    public Binding bindingPersonSave(DirectExchange exchange,
                                     Queue queueSave) {
        return BindingBuilder.bind(queueSave)
                .to(exchange)
                .with("save");
    }

    @Bean
    public Binding bindingPersonSaveOrUpdate(DirectExchange exchange,
                                             Queue queueSaveOrUpdate) {
        return BindingBuilder.bind(queueSaveOrUpdate)
                .to(exchange)
                .with("saveOrUpdate");
    }

    @Bean
    public Binding bindingPersonDelete(DirectExchange exchange,
                                       Queue queueDelete) {
        return BindingBuilder.bind(queueDelete)
                .to(exchange)
                .with("delete");
    }
}
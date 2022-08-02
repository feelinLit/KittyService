package com.itmo.kotiki;

import com.itmo.kotiki.dto.KittyDto;
import com.itmo.kotiki.dto.PersonDto;
import com.itmo.kotiki.entity.*;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class SpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApplication.class, args);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initializeDb(RabbitTemplate template, DirectExchange personExchange, DirectExchange kittyExchange) {
        return (args -> {
            Person admin = new Person(1L, "admin_name", LocalDate.now(), new User("admin", encoder().encode("admin"), Role.ROLE_ADMIN));
            Person user1 = new Person(2L, "user1_name", LocalDate.now(), new User("user1", encoder().encode("user1"), Role.ROLE_USER));
            Person user2 = new Person(3L, "user2_name", LocalDate.now(), new User("user2", encoder().encode("user2"), Role.ROLE_USER));
            template.convertSendAndReceive(personExchange.getName(), "save", PersonDto.convertToDto(admin));
            template.convertSendAndReceive(personExchange.getName(), "save", PersonDto.convertToDto(user1));
            template.convertSendAndReceive(personExchange.getName(), "save", PersonDto.convertToDto(user2));

            Kitty kitty1 = new Kitty("MegaSuperNyash", "Sharik", Color.BLACK, LocalDate.now(), user1);
            Kitty kitty2 = new Kitty("MegaSuperNyash", "Kvadratik", Color.BLACK, LocalDate.now(), user2);
            template.convertSendAndReceive(kittyExchange.getName(), "save", new KittyDto(kitty1));
            template.convertSendAndReceive(kittyExchange.getName(), "save", new KittyDto(kitty2));
        });
    }
}

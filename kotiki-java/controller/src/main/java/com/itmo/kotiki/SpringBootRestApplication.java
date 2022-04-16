package com.itmo.kotiki;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.entity.Role;
import com.itmo.kotiki.repository.KittyRepository;
import com.itmo.kotiki.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class SpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDb(PersonRepository personRepository, KittyRepository kittyRepository) {
        return (args -> {
            Person admin = new Person("admin_name", "admin", "admin", Role.ROLE_ADMIN, LocalDate.now(), null);
            Person user1 = new Person("user1_name", "user1", "user1", Role.ROLE_USER, LocalDate.now(), null);
            Person user2 = new Person("user2_name", "user2", "user2", Role.ROLE_USER, LocalDate.now(), null);
            personRepository.saveAndFlush(admin);
            personRepository.saveAndFlush(user1);
            personRepository.saveAndFlush(user2);

            Kitty kitty1 = new Kitty("MegaSuperNyash", "Sharik", Color.BLACK, LocalDate.now(), user1);
            Kitty kitty2 = new Kitty("MegaSuperNyash", "Kvadratik", Color.BLACK, LocalDate.now(), user2);
            kittyRepository.saveAndFlush(kitty1);
            kittyRepository.saveAndFlush(kitty2);
        });
    }
}

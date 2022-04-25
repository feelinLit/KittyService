package com.itmo.kotiki;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.entity.Role;
import com.itmo.kotiki.service.implementation.KittyServiceImpl;
import com.itmo.kotiki.service.implementation.PersonServiceImpl;
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
}

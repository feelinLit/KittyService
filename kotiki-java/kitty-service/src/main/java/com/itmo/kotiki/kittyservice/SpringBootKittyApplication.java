package com.itmo.kotiki.kittyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.itmo.kotiki.entity")
public class SpringBootKittyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKittyApplication.class, args);
    }
}

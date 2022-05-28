package com.itmo.kotiki.personservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("import com.itmo.kotiki.entity")
public class SpringBootPersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPersonApplication.class, args);
    }
}

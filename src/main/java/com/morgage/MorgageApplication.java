package com.morgage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MorgageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MorgageApplication.class, args);
    }

}

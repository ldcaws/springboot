package com.ldc.scheduledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledemoApplication.class, args);
    }

}

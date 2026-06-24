package com.susol.susolstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SusolstudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SusolstudyApplication.class, args);
    }

}

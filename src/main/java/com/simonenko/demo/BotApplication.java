package com.simonenko.demo;

import com.simonenko.demo.Entity.ChatState;
import com.simonenko.demo.Repository.ChatStateRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.ws.rs.core.Application;

@SpringBootApplication
public class BotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}

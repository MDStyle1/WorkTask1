package com.mds.worktask;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StartApp {
    public static void main(String[] args) {
//        SpringApplication.run(StartApp.class,args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(com.mds.worktask.StartApp.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}

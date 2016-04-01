package com.polyglotapp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.polyglotapp.server.data.model.mongo"})
@EnableJpaRepositories(basePackages = {"com.polyglotapp.server.data.repository.mongo"})

public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);    	 
    }
}
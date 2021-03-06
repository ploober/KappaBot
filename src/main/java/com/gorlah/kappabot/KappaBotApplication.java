package com.gorlah.kappabot;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KappaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(KappaBotApplication.class, args);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

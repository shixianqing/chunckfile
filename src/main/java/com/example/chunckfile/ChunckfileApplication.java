package com.example.chunckfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChunckfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChunckfileApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

//        restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());

        return restTemplate;
    }
}

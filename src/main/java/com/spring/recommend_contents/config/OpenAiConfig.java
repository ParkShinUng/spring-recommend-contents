package com.spring.recommend_contents.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

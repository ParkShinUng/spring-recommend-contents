package com.spring.recommend_contents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:secrets.yml")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RecommendContentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendContentsApplication.class, args);
	}

}

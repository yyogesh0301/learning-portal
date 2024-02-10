package com.yogesh.learningportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class LearningPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPortalApplication.class, args);
	}

}

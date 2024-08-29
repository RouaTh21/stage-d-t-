package com.example.Collaboration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class CollaborationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationApplication.class, args);
	}

}

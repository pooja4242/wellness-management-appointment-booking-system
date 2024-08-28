package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Spring Boot application.
 * The @SpringBootApplication annotation enables auto-configuration, component scanning, and configuration property support.
 */

@SpringBootApplication
public class WellnessManagement4Application {

	public static void main(String[] args) {
		SpringApplication.run(WellnessManagement4Application.class, args); // starts the entire Spring Boot application
	}

}

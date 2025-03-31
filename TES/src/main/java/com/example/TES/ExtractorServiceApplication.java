package com.example.TES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExtractorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExtractorServiceApplication.class, args);
	}
}
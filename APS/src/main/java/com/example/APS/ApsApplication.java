package com.example.APS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ApsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApsApplication.class, args);
	}
}
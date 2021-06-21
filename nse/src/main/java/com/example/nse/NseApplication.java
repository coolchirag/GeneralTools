package com.example.nse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NseApplication.class, args);
	}

}

package com.sanjeeban.CoreApartmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CoreApartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApartmentServiceApplication.class, args);
	}

}

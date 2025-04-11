package com.example.yeon.ecommercejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.yeon.ecommercejava.repository")
//@EntityScan(basePackages = "com.example.yeon.ecommercejava.entity")
public class EcommercejavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercejavaApplication.class, args);
	}

}

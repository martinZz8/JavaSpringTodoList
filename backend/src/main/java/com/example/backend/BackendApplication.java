package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Note: pass "exclude = { SecurityAutoConfiguration.class }" into "@SpringBootApplication()" when "SpringSecurityConfig" isn't in use
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}

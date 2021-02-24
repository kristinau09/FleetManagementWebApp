package com.example.fleetManagementWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class FleetManagementWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetManagementWebAppApplication.class, args);
	}

}

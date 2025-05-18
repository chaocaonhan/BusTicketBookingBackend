package com.example.BusTicketBookingBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BusTicketBookingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusTicketBookingBackendApplication.class, args);

	}

}

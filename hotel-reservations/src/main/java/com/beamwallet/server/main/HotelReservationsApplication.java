package com.beamwallet.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.beamwallet"})
public class HotelReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReservationsApplication.class, args);
	}
}

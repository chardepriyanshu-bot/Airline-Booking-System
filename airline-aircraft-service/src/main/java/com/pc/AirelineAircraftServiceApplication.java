package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class AirelineAircraftServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirelineAircraftServiceApplication.class, args);
	}

}

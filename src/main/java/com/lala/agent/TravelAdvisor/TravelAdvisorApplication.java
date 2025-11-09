package com.lala.agent.TravelAdvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication()
@EnableFeignClients
public class TravelAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAdvisorApplication.class, args);
	}

}

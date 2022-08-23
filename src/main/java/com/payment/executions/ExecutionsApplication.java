package com.payment.executions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExecutionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutionsApplication.class, args);
	}

}

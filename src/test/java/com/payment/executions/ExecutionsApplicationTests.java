package com.payment.executions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.payment.executions.controller.response.ExecutionResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ExecutionsApplicationTests {

	@Autowired
    private WebTestClient webTestClient;
	
	@Test
	void contextLoads() {


		webTestClient
		.get()
		.uri("/executions/5da099c1-5548-4d26-9cf7-d726633b62d3")
		.exchange()
		.expectStatus()
		.is2xxSuccessful()
		.expectBody(ExecutionResponse.class)
		.consumeWith(entityExchangeResult -> {
			ExecutionResponse response = entityExchangeResult.getResponseBody();
			assert response.getId().toString().equals("5da099c1-5548-4d26-9cf7-d726633b62d3");

		});

	}

}

package com.payment.executions.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.payment.executions.controller.request.EntityRequest;
import com.payment.executions.controller.request.StatusRequest;
import com.payment.executions.repository.EntityRepository;
import com.payment.executions.repository.StatusRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class EntityStatusIntegrationTests {

	@Autowired
    private WebTestClient webTestClient;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	private Integer idStatus;
	
	private Integer idEntity;
	
	
	@AfterAll
	void finish () {
		if (idStatus != null) {
			statusRepository.deleteById(idStatus);
		}
		if (idEntity != null) {
			entityRepository.deleteById(idEntity);
		}
	}
	
	@Test
	void createStatus() {

		webTestClient
		.post()
		.uri("/manage/status")
		.bodyValue(StatusRequest.builder().name("TEST").build())
		.exchange()
		.expectStatus()
		.is2xxSuccessful()
		.expectBody(Integer.class)
		.consumeWith(response -> {
			idStatus = response.getResponseBody();
			assert idStatus > 0;

		});
	}
	
	@Test
	void createEntity() {

		webTestClient
		.post()
		.uri("/manage/entity")
		.bodyValue(EntityRequest.builder().name("TESTENTI").build())
		.exchange()
		.expectStatus()
		.is2xxSuccessful()
		.expectBody(Integer.class)
		.consumeWith(response -> {
			idEntity = response.getResponseBody();
			assert idEntity > 0;

		});
	}
	
}

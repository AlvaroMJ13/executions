package com.payment.executions;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.payment.executions.controller.response.ExecutionResponse;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.dao.ExecutionStatusDAO;
import com.payment.executions.dao.ExecutionStatusId;
import com.payment.executions.repository.ExecutionRepository;
import com.payment.executions.repository.ExecutionStatusRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class ExecutionsApplicationTests {

	@Autowired
    private WebTestClient webTestClient;
	
	@Autowired
	private ExecutionRepository executionRepository;
	
	@Autowired
	private ExecutionStatusRepository executionStatusRepository;
	
	private UUID id;
	
	@BeforeAll
	void init () {
		id = executionRepository.save(ExecutionDAO.builder().entityId(1).gtsMessageId(1313).build()).getId();
		executionStatusRepository.save(ExecutionStatusDAO.builder().id(id).statusId(1).timestamp(LocalDateTime.now()).build());
	}
	
	@AfterAll
	void finish () {
		executionStatusRepository.deleteById(ExecutionStatusId.builder().id(id).statusId(1).build());
		executionRepository.deleteById(id);
	}
	
	@Test
	void getById() {


		webTestClient
		.get()
		.uri("/executions/" + id )
		.exchange()
		.expectStatus()
		.is2xxSuccessful()
		.expectBody(ExecutionResponse.class)
		.consumeWith(entityExchangeResult -> {
			ExecutionResponse response = entityExchangeResult.getResponseBody();
			assert response.getId().equals(id);

		});

	}

}

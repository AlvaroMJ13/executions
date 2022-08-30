package com.payment.executions.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.controller.response.ExecutionResponse;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.mapper.ExecutionDAOToExecutionResponse;
import com.payment.executions.service.ExecutionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ExecutionController.EXECUTIONS_URL)
@Slf4j
public class ExecutionController {

	static final String EXECUTIONS_URL = "/executions";
	
	@Autowired
	private ExecutionService executionService;
	
	@GetMapping("/{executionId}")
    public ResponseEntity<ExecutionResponse> getExecutionById(@PathVariable("executionId") String executionId) {

		log.info("Getting execution with id {}", executionId);
		Optional<ExecutionDAO> result = executionService.getExecutionById(UUID.fromString(executionId));
		
		if (result.isPresent()) {
		
			ExecutionDAO execution = result.get();
			ExecutionResponse executionResponse = ExecutionDAOToExecutionResponse.convert(execution);
				
			return ResponseEntity.ok().body(executionResponse);
		} else {
			log.info("No result for execution with id {}", executionId);
			return ResponseEntity.notFound().build();
		}

    }

	
	@PostMapping
	public ResponseEntity<ExecutionResponse> createExecution(@Valid @RequestBody ExecutionRequest executionRequest ) {

		log.info("Creating execution with gtsMessageId {}", executionRequest.getGtsMessageId());

		UUID idStored = null;
		try {
			idStored = executionService.createExecution(executionRequest);
			
			if (executionRequest.getIdExecution() == null) {
				executionService.createSecondStepStatus(1, idStored, executionRequest.getGtsMessageId());	
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ExecutionResponse.builder().error(e.getMessage()).build());

		}
		return ResponseEntity.ok().body(ExecutionResponse.builder().id(idStored).build());

	}
	
}

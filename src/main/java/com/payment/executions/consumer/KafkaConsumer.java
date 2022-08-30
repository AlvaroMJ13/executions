package com.payment.executions.consumer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.executions.consumer.model.ExecutionMessage;
import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.exception.OperationNotAllowed;
import com.payment.executions.service.ExecutionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	@Autowired
	ExecutionService executionService;
	
	@KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void consume (ExecutionMessage executionMessage) {
		log.info("Mensaje leido. gtsMessageId: {}", executionMessage.getGtsMessageId());
		
		try {
			
			Optional<ExecutionDAO> executionDAO = executionService.getExecutionById(UUID.fromString(executionMessage.getIdExecution()));
			
			if (executionDAO.isEmpty()) {
				throw new OperationNotAllowed();
			}
			
			executionService.createExecution(ExecutionRequest.builder()
					.gtsMessageId(executionMessage.getGtsMessageId())
					.idExecution(executionMessage.getIdExecution())
					.build(), executionDAO.get());
		} catch (Exception e) {
			log.error("There was an error trying to update excution with gtsMessageId: {}", executionMessage.getGtsMessageId());
		}
	}
}

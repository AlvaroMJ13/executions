package com.payment.executions.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.executions.consumer.model.ExecutionMessage;
import com.payment.executions.controller.request.ExecutionRequest;
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
			executionService.createExecution(ExecutionRequest.builder()
					.gtsMessageId(executionMessage.getGtsMessageId())
					.operation(executionMessage.getOperation())
					.idExecution(executionMessage.getIdExecution())
					.build());
		} catch (Exception e) {
			log.error("There was an error trying to update excution with gtsMessageId: {}", executionMessage.getGtsMessageId());
		}
	}
}

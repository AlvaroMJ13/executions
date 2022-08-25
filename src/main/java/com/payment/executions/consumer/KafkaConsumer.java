package com.payment.executions.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.executions.consumer.model.ExecutionMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	
	@KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void consume (ExecutionMessage executionMessage) {
		log.info("Mensaje leido. gtsMessageId: {}", executionMessage.getGtsMessageId());
	}
}

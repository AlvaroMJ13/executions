package com.payment.executions.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.payment.executions.kafka.model.RegisterMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerService {

	@Autowired
	KafkaTemplate<String, RegisterMessage> kafkaTemplate;
	
	@Value("${kafka.producer.topic.name}")
	private String producerTopic;
	
	public void sendMessage (RegisterMessage registerMessage) {
		ListenableFuture<SendResult<String, RegisterMessage>> future = kafkaTemplate.send(producerTopic, registerMessage);
		future.addCallback(new ListenableFutureCallback<SendResult<String, RegisterMessage>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.error("Error sending message to topic. IdExecution {}", registerMessage.getIdExecution());
			}

			@Override
			public void onSuccess(SendResult<String, RegisterMessage> result) {
				log.info("Message delivered. IdExecution {}", registerMessage.getIdExecution());

			}
		});
	}
	
}

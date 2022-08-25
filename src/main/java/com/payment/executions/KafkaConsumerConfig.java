package com.payment.executions;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.payment.executions.consumer.model.ExecutionMessage;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	final KafkaProperties kafkaProperties;
	
	public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@Bean
    public ConsumerFactory<String, ExecutionMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
        		kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new JsonDeserializer<>(ExecutionMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExecutionMessage> kafkaListenerContainerFactory() {
   
        ConcurrentKafkaListenerContainerFactory<String, ExecutionMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}

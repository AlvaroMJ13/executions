package com.payment.executions;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.payment.executions.kafka.model.ExecutionMessage;
import com.payment.executions.kafka.model.RegisterMessage;


@EnableKafka
@Configuration
public class KafkaConfig {
	
	final KafkaProperties kafkaProperties;
	
	public KafkaConfig(KafkaProperties kafkaProperties) {
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
    
    @Bean
    public ProducerFactory<String, RegisterMessage> producerFactory() {
    	
    	Map<String, Object> config = kafkaProperties.buildConsumerProperties();
    	config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    	config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
    
    @Bean
    public KafkaTemplate<String, RegisterMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    } 

}

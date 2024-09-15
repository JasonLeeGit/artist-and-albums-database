package com.ltd.coders.software.artist.and.albums.database.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

//@EnableKafka
//@Configuration
//@Profile("Kafka")
public class KafkaConsumerConfig {
	//
	// NO LONGER RERQUIRED SET UP IN APPLICATION.YAML 
	//
	
//	@Bean
//	ConsumerFactory<String, String> consumerFactory() {
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(
//				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
//				"localhost:9092");
//		configProps.put(
//				ConsumerConfig.GROUP_ID_CONFIG, 
//				"my-group-id");
//		configProps.put(
//				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
//				StringDeserializer.class);
//		configProps.put(
//				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
//				StringDeserializer.class);
//		return new DefaultKafkaConsumerFactory<>(configProps);
//	}
//
//	@Bean
//	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = 
//				new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}

}

package com.ltd.coders.software.artist.and.albums.database.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

//@Configuration
//@Profile("Kafka")
public class KafkaProducerConfig {
	//
	// NO LONGER RERQUIRED SET UP IN APPLICATION.YAML 
	//
	
//    @Bean
//    ProducerFactory<String, String> producerFactory() {
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(
//				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
//				"localhost:9092");
//		configProps.put(
//				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
//				StringSerializer.class);
//		configProps.put(
//				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//				StringSerializer.class);
//		return new DefaultKafkaProducerFactory<>(configProps);
//	}
//
//    @Bean
//    KafkaTemplate<String, String> kafkaTemplate() {
//		return new KafkaTemplate<>(producerFactory());
//	}
}

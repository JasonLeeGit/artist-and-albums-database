package com.ltd.coders.software.artist.and.albums.database.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Configuration
@Profile("Kafka")
@Service
public class MessageConsumerService {

	private static final Logger log = LogManager.getLogger(MessageConsumerService.class);

    @KafkaListener(topics = "artists-topic", groupId = "my-group-id")
    public void listen(ConsumerRecord<String, String> message) {  	
    	log.info("Kafka Message Consumed " + message);
    }
}
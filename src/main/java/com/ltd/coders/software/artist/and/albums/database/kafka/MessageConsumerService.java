package com.ltd.coders.software.artist.and.albums.database.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

	private static final Logger log = LogManager.getLogger(MessageConsumerService.class);

    @KafkaListener(topics = "artists-topic", groupId = "my-group-id")
    public void listen(String message) {
    	log.info("Consumed Kafka Message: " + message); 
    }
}
package com.ltd.coders.software.artist.and.albums.database.kafka;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@Testcontainers
public class KafkaTest {

	@Container
	@ServiceConnection
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@Autowired
	private MessageProducerService producer;

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}

	@Test
	public void test() {
		producer.sendMessage("artists-topic", "message one OK");
		producer.sendMessage("artists-topic", "message two Fail");
		producer.sendMessage("artists-topic", "message three OK");

		await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
			// fetch message from DB and check assert statement
		});
	}

}

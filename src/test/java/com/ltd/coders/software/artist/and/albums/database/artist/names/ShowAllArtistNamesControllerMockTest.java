package com.ltd.coders.software.artist.and.albums.database.artist.names;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ltd.coders.software.artist.and.albums.database.ArtistAndAlbums;
import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

@ActiveProfiles("test")
@SpringBootTest(classes = { ArtistAndAlbums.class })
@Testcontainers
public class ShowAllArtistNamesControllerMockTest extends RepositoryForMocksHelper {

	private IShowAllArtistNamesService mockService;
	@Autowired
	private MessageProducerService messageProducerService;
	
	@Container
	@ServiceConnection
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@BeforeEach
	public void setUp() throws Exception {		
		mockService = mock(IShowAllArtistNamesService.class);
		artistNameList = Arrays.asList(ARTIST_NAME_ONE, ARTIST_NAME_TWO, ARTIST_NAME_THREE);
	}

	@Test
	public void showAllArtistNamesTest() {
		when(mockService.getAllArtistNames()).thenReturn(artistNameList);

		ResponseEntity<List<String>> albumNamesResponse = new ShowAllArtistNamesController(mockService,messageProducerService).showAllArtistNames();
		assertEquals(albumNamesResponse.getBody().get(0).toString(), ARTIST_NAME_ONE);
		assertEquals(albumNamesResponse.getBody().get(1).toString(), ARTIST_NAME_TWO);
		assertEquals(albumNamesResponse.getBody().get(2).toString(), ARTIST_NAME_THREE);
	}
	
	@Test
	public void showAllArtistNamesWhenNoneFoundTest() {
		when(mockService.getAllArtistNames()).thenReturn(new ArrayList<String>());

		ResponseEntity<List<String>> albumNamesResponse = new ShowAllArtistNamesController(mockService,messageProducerService).showAllArtistNames();
		assertEquals(albumNamesResponse.getBody().size(), 0);
	}
}

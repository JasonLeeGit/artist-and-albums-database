package com.ltd.coders.software.artist.and.albums.database;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

/**
 * @AutoConfigureMockMvc Use this if you need to configure the web layer for testing but don't need to use MockMvc
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ArtistAndAlbums.class)
@ActiveProfiles({"test"})
@Testcontainers
public abstract class ControllerJsonMapper {
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	protected MockMvc mockMvc;
	protected Artist  artist, artistToReturn;
	protected List<String> artistNameList;
	protected List<Artist> artistList;
	protected List<Album> albumsList;
	protected List<Track> trackList;
	protected static final String VBR = "VBR";
	protected static final String BITRATE = "320";
	protected static final String ALBUM_ONE = "albumOne";
	protected static final String ALBUM_TWO = "albumTwo";
	protected static final String ALBUM_THREE = "albumThree";
	protected static final String ALBUM_FOUR = "albumFour";
	protected static final String ARTIST_NAME_ONE = "nameOne";
	protected static final String ARTIST_NAME_TWO = "nameTwo";
	protected static final String ARTIST_NAME_THREE = "nameThree";
	protected static final String ARTIST_NAME_FOUR = "nameFour";
	protected static final String UPDATED_ARTIST_NAME = "updateArtistName";
	
	@Container
	@ServiceConnection
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
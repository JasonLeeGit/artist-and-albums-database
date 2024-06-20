package com.ltd.coders.software.artist.and.albums.database.artist.names;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ltd.coders.software.artist.and.albums.database.ControllerJsonMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ShowAllArtistNamesControllerRestTest extends ControllerJsonMapper {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@MockBean
	private IShowAllArtistNamesService mockReadAllArtistNamesService;
	
	@BeforeEach	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void showAllArtistNamesTest() throws Exception {
		artistNameList = Arrays.asList(ARTIST_NAME_ONE, ARTIST_NAME_TWO, ARTIST_NAME_THREE);
		
		when(mockReadAllArtistNamesService.getAllArtistNames()).thenReturn(artistNameList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/artist/service/artists/names"))
				.andExpect(status().isOk()).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		String content = mvcResult.getResponse().getContentAsString();
		String[] results = mapFromJson(content, String[].class);
		
		assertTrue(results.length == 3);
		assertEquals(results[0].toString(), ARTIST_NAME_ONE);
		assertEquals(results[1].toString(), ARTIST_NAME_TWO);
		assertEquals(results[2].toString(), ARTIST_NAME_THREE);
	}
	
	public void showAllArtistNamesWhenNoneFoundShouldThrowMismatchedInputExceptionTest() throws Exception {
		when(mockReadAllArtistNamesService.getAllArtistNames()).thenReturn(artistNameList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/artist/service/artists/names"))
				.andExpect(status().isNoContent()).andReturn();
		
		assertEquals(204, mvcResult.getResponse().getStatus());
		
		String content = mvcResult.getResponse().getContentAsString();
		String[] results = mapFromJson(content, String[].class);
		
		assertTrue(results.length == 0);
	}
}

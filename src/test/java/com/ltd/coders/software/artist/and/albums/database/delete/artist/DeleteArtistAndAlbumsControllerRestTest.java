package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
public class DeleteArtistAndAlbumsControllerRestTest extends ControllerJsonMapper {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		super.setUp();		
	}

	@Test
	public void deleteArtistByIdTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-id?id="+1))
				.andExpect(status().isOk()).andReturn();		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteArtistByIncorrectIdTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-id?id="+0))
				.andExpect(status().isBadRequest()).andReturn();		
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	
	
	@Test
	public void deleteArtistByNameTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-name?artistName="+"artistNameOne"))
				.andExpect(status().isOk()).andReturn();		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteArtistByNameEmptyNameTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-name?artistName="+""))
				.andExpect(status().isBadRequest()).andReturn();		
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
}
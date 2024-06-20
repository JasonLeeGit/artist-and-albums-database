package com.ltd.coders.software.artist.and.albums.database.update.artist;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UpdateArtistControllerRestTest extends ControllerJsonMapper {

	@MockBean
	private IUpdateArtistAlbumService mockUpdateArtistAlbumService;
	
	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		super.setUp();
		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).build());
		artist.setAlbums(albumsList);
		artistList = List.of(artist);
		artistToReturn = Artist.builder().artistId(1L).artistName(UPDATED_ARTIST_NAME).build();
	}

	@Test
	public void updateArtistWhenAllDetailsPresentTest() throws Exception {
		when(mockUpdateArtistAlbumService.findArtist(1)).thenReturn(Optional.of(artist));
		when(mockUpdateArtistAlbumService.updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME))
				.thenReturn(artistToReturn);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.put("/v1/artist/service/artist/album-name?artist-id=1&old-album-name="
						+ ARTIST_NAME_ONE + "&new-album-name=" + UPDATED_ARTIST_NAME))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		Artist result = mapFromJson(mvcResult.getResponse().getContentAsString(), Artist.class);

		verify(mockUpdateArtistAlbumService, times(1)).updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);
		assertEquals(result.getArtistName(), UPDATED_ARTIST_NAME);
	}

	public void updateArtistMissingArtistNameTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/artist/service/artist/album-name?artist-id=1&old-album-name=&new-album-name="
						+ UPDATED_ARTIST_NAME))
				.andExpect(status().is4xxClientError()).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
		Artist result = mapFromJson(mvcResult.getResponse().getContentAsString(), Artist.class);
		verify(mockUpdateArtistAlbumService, times(0)).updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);
		assertEquals(result.getArtistName(), ARTIST_NAME_ONE);
	}

	public void updateArtistMissingNewAlbumNameTest() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.put("/v1/artist/service/artist/album-name?artist-id=1&old-album-name="
						+ ARTIST_NAME_ONE + "&new-album-name="))
				.andExpect(status().is4xxClientError()).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
		Artist result = mapFromJson(mvcResult.getResponse().getContentAsString(), Artist.class);
		verify(mockUpdateArtistAlbumService, times(0)).updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);
		assertEquals(result.getArtistName(), ARTIST_NAME_ONE);
	}
}

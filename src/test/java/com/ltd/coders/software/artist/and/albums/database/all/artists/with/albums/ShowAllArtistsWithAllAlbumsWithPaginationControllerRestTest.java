package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ltd.coders.software.artist.and.albums.database.ControllerJsonMapper;
import com.ltd.coders.software.artist.and.albums.database.PageHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ShowAllArtistsWithAllAlbumsWithPaginationControllerRestTest extends ControllerJsonMapper {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@MockBean
	private IShowAllArtistsWithAllAlbumsWithPaginationService mockShowAllArtistsWithAllAlbumsFromDatabaseService;
	
	@BeforeEach
	public void setUp() throws Exception {
		super.setUp();
		artistToReturn = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build(),
				Track.builder().albumName(ALBUM_TWO).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_TWO).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_THREE).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_FOUR).tracks(trackList).build());

		artistToReturn.setAlbums(albumsList);
		artistList = List.of(artistToReturn);
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithDefaultPagination() throws Exception {
		when(mockShowAllArtistsWithAllAlbumsFromDatabaseService.getAllArtistsWithPagination(0, 1))
				.thenReturn(new PageHelper().getArtists(artistList, 0, 1));
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/artist/service/artists/albums/tracks/pagination"))
				.andExpect(status().isOk());
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPaginationAndToLargePageSize() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/artist/service/artists/albums/tracks/pagination?offSet=0&pageSize=11"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPaginationAndIncorrectPageSize() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/artist/service/artists/albums/tracks/pagination?offSet=1&pageSize=0"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPaginationWithToLargeOffSet() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/artist/service/artists/albums/tracks/pagination?offSet=11&pageSize=1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPaginationToSmallOffSet() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/artist/service/artists/albums/tracks/pagination?offSet=-1&pageSize=1"))
				.andExpect(status().isBadRequest());
	}
}

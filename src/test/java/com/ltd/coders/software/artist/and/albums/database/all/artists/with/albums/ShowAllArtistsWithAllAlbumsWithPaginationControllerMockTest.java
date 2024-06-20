package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ltd.coders.software.artist.and.albums.database.PageHelper;
import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ShowAllArtistsWithAllAlbumsWithPaginationControllerMockTest extends RepositoryForMocksHelper {

	private IShowAllArtistsWithAllAlbumsWithPaginationService mockService;
	@Autowired
	private MessageProducerService messageProducerService;	
	
	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		mockService = mock(IShowAllArtistsWithAllAlbumsWithPaginationService.class);

		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build(),
				Track.builder().albumName(ALBUM_TWO).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_TWO).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_THREE).tracks(trackList).build());

		artist.setAlbums(albumsList);
		artistList = List.of(artist);
	}
	
	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPagination() {		
		when(mockService.getAllArtistsWithPagination(0,1)).thenReturn(new PageHelper().getArtists(artistList, 0, 1));

		ResponseEntity<Page<Artist>> results = new ShowAllArtistsWithAllAlbumsWithPaginationController(mockService, messageProducerService)
				.showAllArtistsWithAllAlbumsAndTrackWithPagination(0,1);

		verify(mockService, times(1)).getAllArtistsWithPagination(0,1);
		assertEquals(results.getBody().getContent().size(), 1);
		assertEquals(results.getBody().getContent().get(0).getAlbums().size(), 3);
		assertEquals(results.getBody().getContent().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(2).getAlbumName(), ALBUM_THREE);

		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(1).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getBody().getContent().get(0).getAlbums().get(0).getTracks().get(1).getBitRate(), BITRATE);
	}
	
	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPaginationNoResults() {
		when(mockService.getAllArtistsWithPagination(0,1)).thenReturn(new PageHelper().getArtists(new ArrayList<>(), 0, 1));

		ResponseEntity<Page<Artist>> results = new ShowAllArtistsWithAllAlbumsWithPaginationController(mockService, messageProducerService)
				.showAllArtistsWithAllAlbumsAndTrackWithPagination(0,1);

		verify(mockService, times(1)).getAllArtistsWithPagination(0,1);
		assertEquals(results.getBody().getContent().size(), 0);		
	}
}


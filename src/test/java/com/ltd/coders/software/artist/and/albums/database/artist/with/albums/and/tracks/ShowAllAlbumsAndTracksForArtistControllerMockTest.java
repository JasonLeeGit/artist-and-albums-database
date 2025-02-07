package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShowAllAlbumsAndTracksForArtistControllerMockTest extends RepositoryForMocksHelper {

	private Artist artistToReturn;
	private IShowAllAlbumsAndTracksForArtistService mockService;
	@Autowired
	private MessageProducerService messageProducerService;

	@BeforeEach
	public void setUp() throws Exception {
		mockService = mock(IShowAllAlbumsAndTracksForArtistService.class);
		artistNameList = Arrays.asList(ARTIST_NAME_ONE, ARTIST_NAME_TWO, ARTIST_NAME_THREE);				
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build());
		artistToReturn = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).albums(albumsList).build();
		artistList = List.of(artistToReturn);
	}

	@Test
	public void showAllArtistsNamesTest() {
		when(mockService.getAllAlbumsAndTracksForArtist(ARTIST_NAME_ONE)).thenReturn(artistToReturn);

		ResponseEntity<Artist> response = new ShowAllAlbumsAndTracksForArtistController(mockService, 
				messageProducerService).showAllAlbumsAndTracksForArtist(ARTIST_NAME_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getBitRate().toString(), BITRATE);
	}

	@Test
	public void showAllAlbumNamesForArtist() {
		when(mockService.getAllAlbumsForArtist(ARTIST_NAME_ONE)).thenReturn(artistNameList);

		ResponseEntity<List<String>> response = new ShowAllAlbumsAndTracksForArtistController(mockService, 
				messageProducerService).showAllAlbumNamesForArtist(ARTIST_NAME_ONE);

		assertEquals(response.getBody().get(0).toString(), ARTIST_NAME_ONE);
		assertEquals(response.getBody().get(1).toString(), ARTIST_NAME_TWO);
		assertEquals(response.getBody().get(2).toString(), ARTIST_NAME_THREE);
	}

	@Test
	public void showAllAlbumsAndTracksForArtistNameQuery() {

		when(mockService.getAllAlbumsAndTracksForArtistQuery(ARTIST_NAME_ONE)).thenReturn(artistToReturn);

		ResponseEntity<Artist> response = new ShowAllAlbumsAndTracksForArtistController(mockService, 
				messageProducerService).showAllAlbumsAndTracksForArtistQuery(ARTIST_NAME_ONE);

		assertEquals(response.getBody().getAlbums().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(response.getBody().getAlbums().get(0).getTracks().get(0).getBitRate().toString(), BITRATE);
	}
}

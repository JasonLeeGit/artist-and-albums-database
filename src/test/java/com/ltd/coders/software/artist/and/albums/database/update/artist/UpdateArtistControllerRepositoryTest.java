package com.ltd.coders.software.artist.and.albums.database.update.artist;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.ltd.coders.software.artist.and.albums.database.RepositoryHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

public class UpdateArtistControllerRepositoryTest extends RepositoryHelper {

	private static final String UPDATED_ARTIST_NAME = "Updated Artist Name";
	private Artist artist, artistToReturn;
	private MessageProducerService mockMessageProducerService;

	@BeforeEach
	public void setUp() throws Exception {
		mockMessageProducerService = mock(MessageProducerService.class);
		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).build());
		artist.setAlbums(albumsList);

		artistToReturn = Artist.builder().artistId(1L).artistName(UPDATED_ARTIST_NAME).albums(albumsList).build();
	}

	@Test
	public void updateArtistOrAlbumsDetailsTest() {
		IUpdateArtistAlbumService mockService = mock(IUpdateArtistAlbumService.class);

		when(mockService.findArtist(1)).thenReturn(Optional.of(artist));
		when(mockService.updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME)).thenReturn(artistToReturn);

		ResponseEntity<Artist> results = new UpdateArtistController(mockService, mockMessageProducerService)
				.updateArtistAlbumName(1, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);

		verify(mockService, times(1)).findArtist(1);
		verify(mockService, times(1)).updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);
		assertEquals(results.getBody().getArtistName(), UPDATED_ARTIST_NAME);
	}

}

package com.ltd.coders.software.artist.and.albums.database.update.artist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

public class UpdateArtistControllerMockTest  extends RepositoryForMocksHelper {
	
	private static final String UPDATED_ARTIST_NAME = "Updated Artist Name";
	private Artist artist, artistToReturn;

	@Before
	public void setUp() throws Exception {
		albumsList = List.of(Album.builder().albumId(1L).artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).build());		
		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).albums(albumsList).build();
		artistToReturn = Artist.builder().artistId(1L).artistName(UPDATED_ARTIST_NAME).albums(albumsList).build();
	}
	
	@Test
	public void updateArtistOrAlbumsDetailsTest() {
		IUpdateArtistAlbumService mockService = mock(IUpdateArtistAlbumService.class);
		
		when(mockService.findArtist(1)).thenReturn(Optional.of(artist));
		when(mockService.updateArtist(artist, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME)).thenReturn(artistToReturn);
	
		new UpdateArtistController(mockService).updateArtistAlbumName(1, ARTIST_NAME_ONE, UPDATED_ARTIST_NAME);	
		
		verify(mockService, times(1)).findArtist(1);
		verify(mockService, times(1)).updateArtist(any(Artist.class),any(),any());
		assertEquals(artistToReturn.getArtistName(),UPDATED_ARTIST_NAME);
	}

}

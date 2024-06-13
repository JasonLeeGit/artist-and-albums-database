package com.ltd.coders.software.artist.and.albums.database.insert.artist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.dto.ArtistRequestDto;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;
import com.ltd.coders.software.artist.and.albums.database.exception.ArtistExistsException;

public class InsertNewArtistControllerMockTest extends RepositoryForMocksHelper {

	private ArtistRequestDto artistToSaveRequest;
	private Artist artist;
	private ArtistRequestDto invalidArtistRequestDto;
	private IInsertNewArtistService mockService;
	
	@Before
	public void setUp() {
		mockService = mock(IInsertNewArtistService.class);
		
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build());
		artistToSaveRequest = ArtistRequestDto.builder().artistName(ARTIST_NAME_ONE).albums(albumsList).build();	
		artist = Artist.builder().artistName(ARTIST_NAME_ONE).albums(albumsList).build();
		
		invalidArtistRequestDto = ArtistRequestDto.builder().artistName("").albums(albumsList).build();	
	}

	@Test
	public void insertArtistValidTest() throws ArtistExistsException {
		when(mockService.insertArtist(artist)).thenReturn(artist);
		ResponseEntity<Artist> albumNamesResponse = new InsertNewArtistController(mockService).insertArtist(artistToSaveRequest);
	    
		verify(mockService, times(1)).insertArtist(artist);
		assertEquals(albumNamesResponse.getBody().getAlbums().get(0).getArtistName(), ARTIST_NAME_ONE);
	}
	
	@Test
	public void insertArtistMissingArtistNameTest() throws ArtistExistsException {	
		when(mockService.insertArtist(artist)).thenReturn(artist);
		new InsertNewArtistController(mockService).insertArtist(invalidArtistRequestDto);
	    
		verify(mockService, times(0)).insertArtist(artist);
	}
}

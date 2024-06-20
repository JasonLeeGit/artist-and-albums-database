package com.ltd.coders.software.artist.and.albums.database.artist.names;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ltd.coders.software.artist.and.albums.database.RepositoryHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

/**
 * Test the repository layer
 * NOTE: artistId(1L) used in previous suite test so can't use it again as the DB is used for all tests
 */
public class ShowAllArtistNamesControllerRepositoryTest extends RepositoryHelper {
	
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@BeforeEach
	public void setUp() {
		artistList = new ArrayList<Artist>();
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build());
		artistList = List.of(
				Artist.builder().artistId(2L).artistName(ARTIST_NAME_ONE).albums(albumsList).build(),  
				Artist.builder().artistId(3L).artistName(ARTIST_NAME_TWO).albums(albumsList).build(), 
				Artist.builder().artistId(4L).artistName(ARTIST_NAME_THREE).albums(albumsList).build());
	}

	@Test
	public void showAllArtistNamesTest() {		
		List<Artist> savedAlbums = artistAndAlbumsRepository.saveAll(artistList);
		List<Artist> results = artistAndAlbumsRepository.findAll();

		assertNotNull(savedAlbums);
		assertEquals(results.size(), 3);
		assertEquals(results.get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.get(1).getArtistName(), ARTIST_NAME_TWO);
		assertEquals(results.get(2).getArtistName(), ARTIST_NAME_THREE);
		
		artistAndAlbumsRepository.deleteAll();
	}
	
	@Test
	public void failGracefullyWhenNoResultsReturned() {
		List<Artist> results = artistAndAlbumsRepository.findAll();
		assertEquals(results.size(), 0);
	}

}

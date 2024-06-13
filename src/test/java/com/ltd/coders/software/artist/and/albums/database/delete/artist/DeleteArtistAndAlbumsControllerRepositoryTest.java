package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ltd.coders.software.artist.and.albums.database.RepositoryHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

/**
 * Test the repository layer
 */

public class DeleteArtistAndAlbumsControllerRepositoryTest extends RepositoryHelper {

	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;
	private Artist artist;
	
	@Before
	public void setUp() {
		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();		
		albumsList = List.of(
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).build(), 
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_TWO).build());

		artist.setAlbums(albumsList);
		artistList = List.of(artist);	
	}
	
	@Test
	public void findAlbumByArtistNameTest() {
		Artist savedAlbum = artistAndAlbumsRepository.save(artist);
		
		assertNotNull(savedAlbum);
		artistAndAlbumsRepository.deleteByArtistName(ARTIST_NAME_ONE);

		Artist results = artistAndAlbumsRepository.findByArtistName(ARTIST_NAME_ONE);
		assertNull(results);
	}
}

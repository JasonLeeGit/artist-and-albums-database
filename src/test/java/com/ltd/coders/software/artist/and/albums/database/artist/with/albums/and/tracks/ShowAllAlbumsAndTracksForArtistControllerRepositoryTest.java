package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.After;
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
 */
public class ShowAllAlbumsAndTracksForArtistControllerRepositoryTest extends RepositoryHelper {

	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;
	private Artist artistToSave;

	@BeforeEach
	public void setUp() {
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build());
		artistToSave = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).albums(albumsList).build();
		artistList = List.of(artistToSave);
	}

	@Test
	public void showAllAlbumsAndTracksForArtist() {
		Artist savedAlbum = artistAndAlbumsRepository.save(artistToSave);
		assertNotNull(savedAlbum);
		Artist result = artistAndAlbumsRepository.findByArtistName(ARTIST_NAME_ONE);		
		assertEquals(result.getArtistName(), ARTIST_NAME_ONE);
		assertEquals(result.getAlbums().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(result.getAlbums().get(0).getTracks().size(), 1);
		assertEquals(result.getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(result.getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(result.getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
	}

	@Test
	public void showAllArtistsTest() {
		Artist savedAlbum = artistAndAlbumsRepository.save(artistToSave);
		assertNotNull(savedAlbum);
		Artist result = artistAndAlbumsRepository.findByArtistName(ARTIST_NAME_ONE);
		assertEquals(result.getArtistName(), ARTIST_NAME_ONE);
		assertEquals(result.getAlbums().get(0).getAlbumName(), ALBUM_ONE);
	}
	
	@Test
	public void showAllAlbumsAndTracksForArtistQuery() {
		Artist savedAlbum = artistAndAlbumsRepository.save(artistToSave);
		assertNotNull(savedAlbum);
		Artist result = artistAndAlbumsRepository.findAllAlbumsAndTracksForArtistQuery(ARTIST_NAME_ONE);
		assertEquals(result.getArtistName(), ARTIST_NAME_ONE);
		assertEquals(result.getAlbums().get(0).getAlbumName(), ALBUM_ONE);
	}
	
	@After
	public void cleanUp() {
		artistAndAlbumsRepository.deleteAll();
	}
}

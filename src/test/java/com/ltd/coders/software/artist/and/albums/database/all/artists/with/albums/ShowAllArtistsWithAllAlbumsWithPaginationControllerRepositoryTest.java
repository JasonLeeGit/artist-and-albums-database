package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ltd.coders.software.artist.and.albums.database.RepositoryHelper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

public class ShowAllArtistsWithAllAlbumsWithPaginationControllerRepositoryTest extends RepositoryHelper {

	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;
	private Artist artist;
	private Artist artistTwo;

	@BeforeEach
	public void setUp() throws Exception {
		artistList = new ArrayList<Artist>();
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build(),
				Track.builder().albumName(ALBUM_TWO).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_TWO).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_THREE).tracks(trackList).build(),
				Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_FOUR).tracks(trackList).build());
		
		artist = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();		
		artist.setAlbums(albumsList);
		artistTwo = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).build();
		artistTwo.setAlbums(albumsList);
		
		artistList = List.of(artist, artistTwo);
	}

	@Test
	public void showAllArtistsWithAllAlbumsAndTrackWithPagination() {
		List<Artist> savedAlbums = artistAndAlbumsRepository.saveAll(artistList);
		assertEquals(savedAlbums.size(), 2);

		Page<Artist> results = artistAndAlbumsRepository.findAll(PageRequest.of(0, 1));
		
		assertEquals(results.getSize(), 1);	
		assertEquals(results.getContent().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getBitRate(), BITRATE);
		
		assertEquals(results.getContent().get(0).getAlbums().get(1).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getBitRate(), BITRATE);

		assertEquals(results.getContent().get(0).getAlbums().get(2).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(2).getAlbumName(), ALBUM_THREE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getBitRate(), BITRATE);
		
		assertEquals(results.getContent().get(0).getAlbums().get(3).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(3).getAlbumName(), ALBUM_FOUR);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getAlbumName(), ALBUM_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(0).getArtistName(), ARTIST_NAME_ONE);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getAlbumName(), ALBUM_TWO);
		assertEquals(results.getContent().get(0).getAlbums().get(0).getTracks().get(1).getBitRate(), BITRATE);
	}
	
	@After
	public void cleanUp(){
		artistAndAlbumsRepository.deleteAll();
	}
}

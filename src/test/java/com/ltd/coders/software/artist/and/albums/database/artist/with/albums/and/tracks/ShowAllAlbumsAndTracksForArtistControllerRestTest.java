package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ltd.coders.software.artist.and.albums.database.ControllerJsonMapper;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

/**
 * Test the controller and service layer
 */
public class ShowAllAlbumsAndTracksForArtistControllerRestTest extends ControllerJsonMapper {

	@MockBean
	private IShowAllAlbumsAndTracksForArtistService mockShowAllAlbumNamesForArtistFromDatabaseService;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		trackList = List.of(Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(BITRATE).build(),
				Track.builder().albumName(ALBUM_ONE).artistName(ARTIST_NAME_ONE).bitRate(VBR).build());
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).tracks(trackList).build());	
		artistToReturn = Artist.builder().artistId(1L).artistName(ARTIST_NAME_ONE).albums(albumsList).build();
	}
	
	@Test
	public void showAllAlbumNamesForArtistTest() throws Exception {
		artistNameList = Arrays.asList(ALBUM_ONE, ALBUM_TWO, ALBUM_THREE); 
		
		when(mockShowAllAlbumNamesForArtistFromDatabaseService.getAllAlbumsForArtist(ARTIST_NAME_ONE)).thenReturn(artistNameList);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/artist/service/artist/album-names?artistName=" + ARTIST_NAME_ONE))
				.andExpect(status().isOk()).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());

		String content = mvcResult.getResponse().getContentAsString();
		String[] results = mapFromJson(content, String[].class);
		assertNotNull(results);
		assertEquals(results[0].toString(), ALBUM_ONE);
		assertEquals(results[1].toString(), ALBUM_TWO);
		assertEquals(results[2].toString(), ALBUM_THREE);
	}

	@Test
	public void showAllAlbumsAndTracksForArtistTest() throws Exception {

		when(mockShowAllAlbumNamesForArtistFromDatabaseService.getAllAlbumsAndTracksForArtist(ARTIST_NAME_ONE))
				.thenReturn(artistToReturn);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/v1/artist/service/artist/albums/tracks?artistName=" + ARTIST_NAME_ONE))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		String content = mvcResult.getResponse().getContentAsString();
		Artist results = mapFromJson(content, Artist.class);
		assertNotNull(results);
		assertEquals(results.getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(results.getAlbums().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(results.getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getAlbums().get(0).getTracks().get(1).getBitRate(), VBR);
	}
	
	@Test
	public void showAllAlbumsAndTracksForArtistQueryTest() throws Exception {

		when(mockShowAllAlbumNamesForArtistFromDatabaseService.getAllAlbumsAndTracksForArtistQuery(ARTIST_NAME_ONE))
				.thenReturn(artistToReturn);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/v1/artist/service/artist/albums/tracks/query?artistName=" + ARTIST_NAME_ONE))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

		String content = mvcResult.getResponse().getContentAsString();
		Artist results = mapFromJson(content, Artist.class);
		assertNotNull(results);
		assertEquals(results.getArtistName().toString(), ARTIST_NAME_ONE);
		assertEquals(results.getAlbums().get(0).getAlbumName().toString(), ALBUM_ONE);
		assertEquals(results.getAlbums().get(0).getTracks().size(), 2);
		assertEquals(results.getAlbums().get(0).getTracks().get(0).getBitRate(), BITRATE);
		assertEquals(results.getAlbums().get(0).getTracks().get(1).getBitRate(), VBR);
	}
}

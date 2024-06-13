package com.ltd.coders.software.artist.and.albums.database.insert.artist;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltd.coders.software.artist.and.albums.database.ControllerJsonMapper;
import com.ltd.coders.software.artist.and.albums.database.dto.ArtistRequestDto;
import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

//@RunWith(SpringRunner.class)
public class InsertNewArtistControllerRestTest extends ControllerJsonMapper {
	
	@MockBean
	private IInsertNewArtistService mockInsertNewArtistService;
	private ArtistRequestDto validArtistRequestDto;
	private ArtistRequestDto invalidArtistRequestDto;
	private Artist validArtistToSave;
	private Artist invalidArtistToSave;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		validArtistRequestDto = ArtistRequestDto.builder().artistName(ARTIST_NAME_ONE).build();
		albumsList = List.of(Album.builder().artistName(ARTIST_NAME_ONE).albumName(ALBUM_ONE).build());
		validArtistRequestDto.setAlbums(albumsList);	
		validArtistToSave = Artist.builder().artistName(ARTIST_NAME_ONE).albums(albumsList).build();		
		invalidArtistRequestDto = ArtistRequestDto.builder().artistName("").build();
		invalidArtistToSave = Artist.builder().artistName("").albums(albumsList).build();	
	}

	@Test
	public void insertNewValidArtistWithAlbumsAndTracks() throws Exception {
		when(mockInsertNewArtistService.insertArtist(validArtistRequestDto.toArtist())).thenReturn(validArtistToSave);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/artist/service/artist")
				.content(asJsonString(validArtistRequestDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
        verify(mockInsertNewArtistService, times(1)).insertArtist(validArtistToSave);
	}

	@Test
	public void insertNewInValidArtistWithAlbumsAndTracks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/artist/service/artist")
				.content(asJsonString(invalidArtistRequestDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
        verify(mockInsertNewArtistService, times(0)).insertArtist(invalidArtistToSave);
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

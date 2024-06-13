package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ltd.coders.software.artist.and.albums.database.ControllerJsonMapper;

public class DeleteArtistAndAlbumsControllerRestTest extends ControllerJsonMapper {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();		
	}

	@Test
	public void deleteArtistByIdTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-id?id="+1))
				.andExpect(status().isOk()).andReturn();		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteArtistByIncorrectIdTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-id?id="+0))
				.andExpect(status().isBadRequest()).andReturn();		
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	
	
	@Test
	public void deleteArtistByNameTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-name?artistName="+"artistNameOne"))
				.andExpect(status().isOk()).andReturn();		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteArtistByNameEmptyNameTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/artist/service/artist/artist-name?artistName="+""))
				.andExpect(status().isBadRequest()).andReturn();		
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
}
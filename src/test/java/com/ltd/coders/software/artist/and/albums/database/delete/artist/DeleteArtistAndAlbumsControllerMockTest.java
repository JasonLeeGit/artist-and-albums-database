package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ltd.coders.software.artist.and.albums.database.RepositoryForMocksHelper;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteArtistAndAlbumsControllerMockTest extends RepositoryForMocksHelper {

	private IDeleteArtistAndAlbumsService mockService;
	@Autowired
	private MessageProducerService messageProducerService;

	@BeforeEach
	public void setUp() throws Exception {
		mockService = mock(IDeleteArtistAndAlbumsService.class);
		messageProducerService = mock(MessageProducerService.class);
	}

	@Test
	public void deleteArtistByIdTest() {
		new DeleteArtistAndAlbumsController(mockService, messageProducerService).deleteArtistById(2);
		verify(mockService, times(1)).deleteById(2);
		verify(mockService, times(0)).deleteById(1);
	}

	@Test
	public void deleteArtistByArtistNameTest() {
		new DeleteArtistAndAlbumsController(mockService, messageProducerService)
				.deleteArtistByArtistName(ARTIST_NAME_ONE);
		verify(mockService, times(1)).deleteByArtistName(ARTIST_NAME_ONE);
		verify(mockService, times(0)).deleteByArtistName(ARTIST_NAME_TWO);
	}
}

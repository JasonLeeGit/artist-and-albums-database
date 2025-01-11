package com.ltd.coders.software.artist.and.albums.database.test.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;

import com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums.ShowAllArtistsWithAllAlbumsWithPaginationControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums.ShowAllArtistsWithAllAlbumsWithPaginationControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums.ShowAllArtistsWithAllAlbumsWithPaginationControllerRestTest;
import com.ltd.coders.software.artist.and.albums.database.artist.names.ShowAllArtistNamesControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.artist.names.ShowAllArtistNamesControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.artist.names.ShowAllArtistNamesControllerRestTest;
import com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks.ShowAllAlbumsAndTracksForArtistControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks.ShowAllAlbumsAndTracksForArtistControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks.ShowAllAlbumsAndTracksForArtistControllerRestTest;
import com.ltd.coders.software.artist.and.albums.database.delete.artist.DeleteArtistAndAlbumsControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.delete.artist.DeleteArtistAndAlbumsControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.delete.artist.DeleteArtistAndAlbumsControllerRestTest;
import com.ltd.coders.software.artist.and.albums.database.insert.artist.InsertNewArtistControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.insert.artist.InsertNewArtistControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.insert.artist.InsertNewArtistControllerRestTest;
import com.ltd.coders.software.artist.and.albums.database.update.artist.UpdateArtistControllerMockTest;
import com.ltd.coders.software.artist.and.albums.database.update.artist.UpdateArtistControllerRepositoryTest;
import com.ltd.coders.software.artist.and.albums.database.update.artist.UpdateArtistControllerRestTest;

@ActiveProfiles("test")
@Suite
@SelectClasses({ 
	ShowAllArtistsWithAllAlbumsWithPaginationControllerMockTest.class, 
	ShowAllArtistsWithAllAlbumsWithPaginationControllerRepositoryTest.class, 
	ShowAllArtistsWithAllAlbumsWithPaginationControllerRestTest.class,
	
	ShowAllArtistNamesControllerMockTest.class,
	ShowAllArtistNamesControllerRepositoryTest.class,
	ShowAllArtistNamesControllerRestTest.class,

	ShowAllAlbumsAndTracksForArtistControllerRepositoryTest.class,
	ShowAllAlbumsAndTracksForArtistControllerMockTest.class,
	ShowAllAlbumsAndTracksForArtistControllerRestTest.class,
	
	DeleteArtistAndAlbumsControllerMockTest.class,
	DeleteArtistAndAlbumsControllerRepositoryTest.class,
	DeleteArtistAndAlbumsControllerRestTest.class,
	
	InsertNewArtistControllerMockTest.class,
	InsertNewArtistControllerRepositoryTest.class,
	InsertNewArtistControllerRestTest.class,

	UpdateArtistControllerMockTest.class,
	UpdateArtistControllerRepositoryTest.class,
	UpdateArtistControllerRestTest.class
})
public class JunitTestSuite
{
}

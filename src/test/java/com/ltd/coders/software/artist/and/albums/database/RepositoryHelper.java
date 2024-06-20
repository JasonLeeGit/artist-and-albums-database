package com.ltd.coders.software.artist.and.albums.database;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ArtistAndAlbums.class)
@ActiveProfiles("test")
public abstract class RepositoryHelper extends RepositoryForMocksHelper {
	
}

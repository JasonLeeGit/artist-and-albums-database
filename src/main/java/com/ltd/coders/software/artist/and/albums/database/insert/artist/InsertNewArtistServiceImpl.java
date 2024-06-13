package com.ltd.coders.software.artist.and.albums.database.insert.artist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.exception.ArtistExistsException;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

@Service
public class InsertNewArtistServiceImpl implements IInsertNewArtistService {

	private static final Logger log = LogManager.getLogger(InsertNewArtistServiceImpl.class);

	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@Override
	public Artist insertArtist(Artist artist) throws ArtistExistsException {
		Artist insertedArtist = null;
		if (artist.getArtistName() != null) {
			if (!artistExistInDatabase(artist.getArtistName())) {
				log.error("Artist Does Not Exist Inserting New Artist... ");
				insertedArtist = artistAndAlbumsRepository.save(artist);
			}
		}
		return insertedArtist;
	}

	private boolean artistExistInDatabase(String artistName) throws ArtistExistsException {
		if (artistAndAlbumsRepository.findByArtistName(artistName) == null) {
			return false;
		} else {
			throw new ArtistExistsException("Artist " + artistName + " already exists in the database ");
		}
	}
}
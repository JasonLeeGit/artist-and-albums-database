package com.ltd.coders.software.artist.and.albums.database.update.artist;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

import jakarta.transaction.Transactional;

@Service
public class UpdateArtistAlbumServiceImpl implements IUpdateArtistAlbumService {

	private static final Logger log = LogManager.getLogger(UpdateArtistAlbumServiceImpl.class);
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@Override
	public Optional<Artist> findArtist(int id) {
		log.info("UpdateArtistAlbumServiceImpl.findArtist()");
		return artistAndAlbumsRepository.findById(id);
	}

	@Override
	@Transactional
	public Artist updateArtist(Artist artist, String oldAlbumName, String newAlbumName) {
		log.info("UpdateArtistAlbumServiceImpl.updateArtist()");
		if (parmatersValid(oldAlbumName, newAlbumName)) {
			log.error("Halted Possible SQL Injection!!!");
		} else {
			artist.getAlbums().forEach(a -> {
				if (a.getAlbumName().equalsIgnoreCase(oldAlbumName)) {
					log.info("Updating album name");
					a.setAlbumName(newAlbumName);
				}
			});
		}
		return artistAndAlbumsRepository.save(artist);
	}

	private boolean parmatersValid(String oldAlbumName, String newAlbumName) {
		return oldAlbumName.contains("artist") || newAlbumName.contains("artist");
	}
}

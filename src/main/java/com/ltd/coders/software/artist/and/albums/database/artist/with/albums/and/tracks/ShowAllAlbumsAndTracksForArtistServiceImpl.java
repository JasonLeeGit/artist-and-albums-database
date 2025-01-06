package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

@Service
public class ShowAllAlbumsAndTracksForArtistServiceImpl implements IShowAllAlbumsAndTracksForArtistService {

	private static final Logger log = LogManager.getLogger(ShowAllAlbumsAndTracksForArtistServiceImpl.class);
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@Override
	public List<String> getAllAlbumsForArtist(String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistServiceImpl.getAllAlbumsForArtist()");
		List<String> albumNamesForArtist = null;
		if (artistName.contains("artist")) {
			log.error("Halted Possible SQL Injection");
		} else {
			Artist artist = artistAndAlbumsRepository.findByArtistName(artistName);
			if(artist != null) {
				albumNamesForArtist = artist.getAlbums()
						.stream()
						.map(al -> al.getAlbumName())
						.toList();
				}
		}
		return albumNamesForArtist;
	}

	@Override
	public Artist getAllAlbumsAndTracksForArtist(String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistServiceImpl.getAllAlbumsAndTracksForArtist()");
		Artist artistAlbums = null;
		if (artistName.contains("artist")) {
			log.error("Halted Possible SQL Injection");
		} else {
			artistAlbums = artistAndAlbumsRepository.findByArtistName(artistName);
		}
		return artistAlbums;
	}

	@Override
	public Artist getAllAlbumsAndTracksForArtistQuery(String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistServiceImpl.getAllAlbumsAndTracksForArtistQuery()");
		Artist artistAlbums = null;
		if (artistName.contains("jpaartist")) {
			log.error("Halted Possible SQL Injection");
		} else {
			artistAlbums = artistAndAlbumsRepository.findAllAlbumsAndTracksForArtistQuery(artistName);
		}
		return artistAlbums;
	}
}

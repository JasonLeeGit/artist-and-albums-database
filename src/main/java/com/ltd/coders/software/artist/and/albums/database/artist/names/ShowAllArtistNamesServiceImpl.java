package com.ltd.coders.software.artist.and.albums.database.artist.names;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

@Service
public class ShowAllArtistNamesServiceImpl implements IShowAllArtistNamesService {

	private static final Logger log = LogManager.getLogger(ShowAllArtistNamesServiceImpl.class);
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@Override
	@Cacheable("allArtistNames")
	public List<String> getAllArtistNames() {
		List<String> artistNames = null;
		List<Artist> artists = artistAndAlbumsRepository.findAll();
		if (artists != null) {
			artistNames = artists.stream()
					.map(artist -> artist.getArtistName())
					.distinct()
					.collect(toList());
		}
		log.error("ShowAllArtistNamesServiceImpl.getAllArtistNames()");
		return artistNames;
	}
}

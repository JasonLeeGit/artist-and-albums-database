package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

@Service
public class ShowAllArtistsWithAllAlbumsWithPaginationServiceImpl implements IShowAllArtistsWithAllAlbumsWithPaginationService {

	private static final Logger log = LogManager.getLogger(ShowAllArtistsWithAllAlbumsWithPaginationServiceImpl.class);
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;
	
	@Override
	@Cacheable("allArtistsPagination")
	public Page<Artist> getAllArtistsWithPagination(int offset, int pageSize) {
		log.info("ShowAllArtistsWithAllAlbumsServiceImpl.getAllArtists()");
		return artistAndAlbumsRepository.findAll(PageRequest.of(offset, pageSize));
	}
}

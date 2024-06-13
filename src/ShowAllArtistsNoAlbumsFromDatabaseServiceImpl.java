package com.ltd.coders.software.artist.and.albums.mysql.all.artists.no.albums;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.mysql.model.JPAArtist;

@Service
public class ShowAllArtistsNoAlbumsFromDatabaseServiceImpl implements IShowAllArtistsNoAlbumsFromDatabaseService {

	@Autowired
	private ShowAllArtistsNoAlbumsFromDatabaseRepository showAllArtistsFromDatabaseRepository;

	@Override
	public List<JPAArtist> getAllArtists() {
		return showAllArtistsFromDatabaseRepository.getAllArtists();
	}
}

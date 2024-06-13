package com.ltd.coders.software.artist.and.albums.database.artist.names;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IShowAllArtistNamesService {
	List<String> getAllArtistNames();
}

package com.ltd.coders.software.artist.and.albums.database.insert.artist;

import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.exception.ArtistExistsException;

@Service
public interface IInsertNewArtistService{

	Artist insertArtist(Artist artist) throws ArtistExistsException;
}

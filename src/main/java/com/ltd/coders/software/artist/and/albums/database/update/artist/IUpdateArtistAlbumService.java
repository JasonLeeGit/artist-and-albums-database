package com.ltd.coders.software.artist.and.albums.database.update.artist;

import java.util.Optional;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

public interface IUpdateArtistAlbumService {

	Optional<Artist> findArtist(int id);

	Artist updateArtist(Artist artist, String oldAlbumName, String newAlbumName);
}

package com.ltd.coders.software.artist.and.albums.database.delete.artist;

public interface IDeleteArtistAndAlbumsService {

	void deleteById(int id);

	void deleteByArtistName(String artistName);
}

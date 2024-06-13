package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import java.util.List;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

public interface IShowAllAlbumsAndTracksForArtistService {

	List<String> getAllAlbumsForArtist(String artistName);

	Artist getAllAlbumsAndTracksForArtist(String artistName);

	Artist getAllAlbumsAndTracksForArtistQuery(String artistName);
}

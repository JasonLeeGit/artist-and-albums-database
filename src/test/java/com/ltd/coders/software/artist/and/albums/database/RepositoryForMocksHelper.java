package com.ltd.coders.software.artist.and.albums.database;

import java.util.List;

import org.springframework.test.context.ActiveProfiles;

import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

@ActiveProfiles("test")
public abstract class RepositoryForMocksHelper {
	
	protected Artist artist;
	protected Album albumOne;
	protected Album albumTwo;
	protected Album albumThree;
	protected Album albumFour;
	protected List<String> artistNameList;
	protected List<Artist> artistList;
	protected List<Album> albumsList;
	protected List<Track> trackList;
	protected static final String BITRATE = "320";
	protected static final String ALBUM_ONE = "albumNameOne";
	protected static final String ALBUM_TWO = "albumNameTwo";
	protected static final String ALBUM_THREE = "albumNameThree";
	protected static final String ALBUM_FOUR = "albumNameFour";
	protected static final String ARTIST_NAME_ONE = "artistNameOne";
	protected static final String ARTIST_NAME_TWO = "artistNameTwo";
	protected static final String ARTIST_NAME_THREE = "artistNameThree";
	protected static final String ARTIST_NAME_FOUR = "artistNameFour";
}

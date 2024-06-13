package com.ltd.coders.software.artist.and.albums.database.initialize;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.helper.AlbumTrackDetails;
import com.ltd.coders.software.artist.and.albums.database.helper.ConfigPropertiesHelper;

@Service
public class ReadDirectoryFromHardDriveAndReturnDataServiceImpl implements IReadDirectoryFromHardDriveAndReturnDataService {

	private static final Logger log = LogManager.getLogger(ReadDirectoryFromHardDriveAndReturnDataServiceImpl.class);
	@Autowired
	private ConfigPropertiesHelper properties;
	@Autowired
	private AlbumTrackDetails addAlbumTrackDetails;

	public List<Artist> directoryList() {
		
		log.error("ReadDirectoryFromHardDriveAndReturnDataServiceImpl.directoryList(), musicPath "+properties.getMusicPath());
		
		List<Artist> artistsList = new ArrayList<>();
		try {
			List<String> artistNames = Files.list(new File(properties.getMusicPath()).toPath())
					.map(path -> path.getFileName().toString())
					.collect(toList());

			Collections.sort(artistNames, String.CASE_INSENSITIVE_ORDER);

			artistNames.forEach(artistName -> {
				try {
					if (isValidFileType(artistName)) {
						log.error("reading artists name " + artistName);
						Artist artist = new Artist();
						artist.setArtistName(artistName);

						List<String> albumNames = Files.list(new File(properties.getMusicPath() + artistName).toPath())
								.map(p -> p.getFileName().toString())
								.collect(toList());

						List<Album> albumList = new ArrayList<>();
						albumNames.forEach(albumName -> {

							if (isValidFileType(albumName)) {
								Path path = new File(properties.getMusicPath() + artistName + "\\" + albumName + "\\")
										.toPath();
								
								Album album = addAlbumTrackDetails.addAlbumDetails(path, artistName, albumName);

								if (album != null) {
									if (albumName.contains("'")) {
										albumName = cleanNameOfSingleQuotes(albumName);
									}
									album.setArtistName(artistName);
									album.setAlbumName(albumName);

									albumList.add(album);
								}
							}
						});
						artist.setAlbums(albumList);

						if (artist != null) {
							artistsList.add(artist);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artistsList;
	}

	public String cleanNameOfSingleQuotes(String name) {
		String cleanName = "";
		for (int i = 0; i < name.length(); ++i) {
			char nameCharacter = name.charAt(i);
			int isNameCharacterSingleQuote = (int) nameCharacter;
			if (isNameCharacterSingleQuote != 39) {
				cleanName = cleanName + nameCharacter;
			}
		}
		return cleanName;
	}

	public boolean isValidFileType(String value) {
		return !value.contains(".jpg")     && !value.contains(".JPG") && !value.contains(".jpeg")
				&& !value.contains(".ini") && !value.contains(".pdf") && !value.contains(".avi")
				&& !value.contains(".txt") && !value.contains(".bmp") && !value.contains(".url")
				&& !value.contains(".png") && !value.contains(".gif") && !value.contains(".ini")
				&& !value.contains(".db")  && !value.contains("iTunes");
	}
}

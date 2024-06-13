package com.ltd.coders.software.artist.and.albums.database.initialize;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

@Service
public interface IWriteDataToDatabaseService {
	void saveAndFlush(Artist artist);

	void saveAll(List<Artist> artist);

	long isRepositoryPopulated();
}

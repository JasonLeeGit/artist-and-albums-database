package com.ltd.coders.software.artist.and.albums.database.initialize;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

import jakarta.transaction.Transactional;

@Service
public class WriteDataToDatabaseServiceImpl implements IWriteDataToDatabaseService {

	private static final Logger log = LogManager.getLogger(WriteDataToDatabaseServiceImpl.class);
	@Autowired
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;

	@Override
	@Transactional
	public void saveAll(List<Artist> artists) {
		log.error("WriteDataToDatabaseServiceImpl.saveAll()");
		artistAndAlbumsRepository.saveAll(artists);
	}

	@Override
	@Transactional
	public void saveAndFlush(Artist artist) {
		log.error("WriteDataToDatabaseServiceImpl.saveAndFlush()");
		artistAndAlbumsRepository.saveAndFlush(artist);
	}

	@Override
	public long isRepositoryPopulated() {
		log.error("checking if repository is populated count = " + artistAndAlbumsRepository.count());
		return artistAndAlbumsRepository.count();
	}
}

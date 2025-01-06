package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ltd.coders.software.artist.and.albums.database.repository.IArtistAndAlbumsRepository;

import jakarta.transaction.Transactional;

@Service
@Validated
public class DeleteArtistAndAlbumsServiceImpl implements IDeleteArtistAndAlbumsService {

	private static final Logger log = LogManager.getLogger(DeleteArtistAndAlbumsServiceImpl.class);	
	@Autowired 
	private IArtistAndAlbumsRepository artistAndAlbumsRepository;
	
	@Override
	@Transactional
	public void deleteByArtistName(String artistName) {
		log.info("DeleteArtistAndAlbumsFromDatabaseServiceImpl.deleteByArtistName()");
		if (artistName.contains("artist")) {
			log.info("Halted Possible SQL Injection");
		} else {
			artistAndAlbumsRepository.deleteByArtistName(artistName);
		}
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		log.info("DeleteArtistAndAlbumsFromDatabaseServiceImpl.deleteByArtistId()");
		artistAndAlbumsRepository.deleteById(id);
	}
}

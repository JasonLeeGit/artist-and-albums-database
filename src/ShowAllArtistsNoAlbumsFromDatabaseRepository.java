package com.ltd.coders.software.artist.and.albums.mysql.all.artists.no.albums;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ltd.coders.software.artist.and.albums.mysql.model.JPAArtist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class ShowAllArtistsNoAlbumsFromDatabaseRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
	@SuppressWarnings("unchecked")
	public List<Artist> getAllArtists() {
		Query query = entityManager.createNativeQuery(
				"SELECT id, artist_name FROM ARTIST_AND_ALBUMS.ARTIST order by id", Artist.class);
		return query.getResultList();
	}
}

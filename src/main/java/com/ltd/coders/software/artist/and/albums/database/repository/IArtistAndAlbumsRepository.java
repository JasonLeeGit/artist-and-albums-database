package com.ltd.coders.software.artist.and.albums.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

@Repository("artistRepository")
public interface IArtistAndAlbumsRepository extends JpaRepository<Artist, Integer> {

	// Not JpaRepository this generates something like SELECT * FROM Album WHERE artistName = ?
	Artist findByArtistName(String artistName);

	// Not JpaRepository this generates something like DELET FROM Album WHERE artistName = ?
	@Modifying
	void deleteByArtistName(String artistName);

	@Query(value = "SELECT * FROM artist where artist_name = :artistName", nativeQuery = true)
	Artist findAllAlbumsAndTracksForArtistQuery(@Param("artistName") String artistName);

	@Modifying
	@Query(value = "DELETE FROM album where artist_name = :artistName and album_name = :albumName", nativeQuery = true)
	void deleteAlbumByAlbumName(@Param("artistName") String artistName, @Param("albumName") String albumName);

	@SuppressWarnings("unchecked")
	Artist save(Artist artist);
}
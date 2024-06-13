package com.ltd.coders.software.artist.and.albums.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ArtistAndAlbums {

	public static void main(String[] args) {
		SpringApplication.run(ArtistAndAlbums.class, args);
	}
}

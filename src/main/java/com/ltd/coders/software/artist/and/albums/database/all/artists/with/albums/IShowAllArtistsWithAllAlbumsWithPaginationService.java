package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import org.springframework.data.domain.Page;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

public interface IShowAllArtistsWithAllAlbumsWithPaginationService {

	Page<Artist> getAllArtistsWithPagination(int offset, int pageSize);
}

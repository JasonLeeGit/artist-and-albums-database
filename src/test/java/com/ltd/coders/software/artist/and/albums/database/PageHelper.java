package com.ltd.coders.software.artist.and.albums.database;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

@ActiveProfiles("test")
public class PageHelper {

	public Page<Artist> getArtists(List<Artist> artistList, int page, int size) {

		Pageable pageable = createPageRequestUsing(page, size);

		List<Artist> allArtists = artistList;
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), allArtists.size());

		List<Artist> pageContent = allArtists.subList(start, end);
		return new PageImpl<>(pageContent, pageable, allArtists.size());
	}

	public Pageable createPageRequestUsing(int page, int size) {
		return PageRequest.of(page, size);
	}
}

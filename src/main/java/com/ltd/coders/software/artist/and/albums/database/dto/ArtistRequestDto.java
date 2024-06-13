package com.ltd.coders.software.artist.and.albums.database.dto;

import java.util.List;

import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRequestDto {

	@NotBlank(message = "artist name cannot be empty")
	private String artistName;

	@NotEmpty(message = "No album has been added for ArtistRequest")
	private List<Album> albums;

	public Artist toArtist() {
		return Artist.builder()
				.artistName(artistName)
				.albums(albums).build();
	}
}
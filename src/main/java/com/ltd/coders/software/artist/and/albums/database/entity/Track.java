package com.ltd.coders.software.artist.and.albums.database.entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRACK")
@Schema(name = "ARTIST_AND_ALBUMS")
public class Track implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "track_id")
	private Long trackId;
	@NotBlank(message = "artist name cannot be null or empty")
	private String artistName;
	@NotBlank(message = "album name cannot be null or empty")
	private String albumName;

	private String bitRate;

	private boolean isVariableBitRate;

	private String genre;

	private String title;

	private String fileFormat;

	private String recordLabel;

	private String releaseYear;

	private String trackLength;

	private String trackNumber;
}

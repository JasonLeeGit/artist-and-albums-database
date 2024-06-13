package com.ltd.coders.software.artist.and.albums.database.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @NoArgsConstructor and @AllArgsConstructor used for builder
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ALBUM")
@Schema(name = "ARTIST_AND_ALBUMS")
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "album_id")
	private Long albumId;

	@NotBlank(message = "album name cannot be null or empty for Album")
	private String albumName;
	
	@NotBlank(message = "artist name cannot be null or empty for Album")
	private String artistName;

	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "fk_album_id", referencedColumnName = "album_id")
	private List<Track> tracks;

}

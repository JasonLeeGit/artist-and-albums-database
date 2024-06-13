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
 * @author softw An artist can have many albums so one artist list of albums
 * @NoArgsConstructor and @AllArgsConstructor used for builder
 * 
 * below creates the column fk_artist_id on the album table,
 * and links it to artist_id in the artist table
 * @JoinColumn(name = "fk_artist_id", referencedColumnName = "artist_id")
 */

@Data
@Builder //(staticName = "build") instead of @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ARTIST")
@Schema(name = "ARTIST_AND_ALBUMS")
public class Artist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "artist_id")
	private Long artistId;
	
	@NotBlank(message = "artist name cannot be null or empty for Artist")
	private String artistName;

	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "fk_artist_id", referencedColumnName = "artist_id")
	private List<Album> albums;

}

package com.ltd.coders.software.artist.and.albums.database.update.artist;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Tag(name = "Update Artist Album Name")
@Validated
@RestController
@RequestMapping("/v1/artist/service")
public class UpdateArtistController {

	private static final Logger log = LogManager.getLogger(UpdateArtistController.class);

	private IUpdateArtistAlbumService updateArtistAlbumService;
	private MessageProducerService messageProducerService;

	public UpdateArtistController(IUpdateArtistAlbumService updateArtistAlbumService,
			MessageProducerService messageProducerService) {
		this.updateArtistAlbumService = updateArtistAlbumService;
		this.messageProducerService = messageProducerService;
	}

	@Operation
	@PutMapping("/artist/album-name")
	public ResponseEntity<Artist> updateArtistAlbumName(
			@RequestParam("artist-id") @Positive(message = "id must be greater than zero") int id,
			@RequestParam("old-album-name") @NotBlank(message = "old album name cannot be null or empty") String oldAlbumName,
			@RequestParam("new-album-name") @NotBlank(message = "new album name cannot be null or empty") String newAlbumName) {

		log.info("UpdateArtistController.updateArtistAlbumName()");

		Optional<Artist> artist = updateArtistAlbumService.findArtist(id);
		if (artist.isPresent()) {
			Artist updatedArtist = updateArtistAlbumService.updateArtist(artist.get(), oldAlbumName, newAlbumName);
			if (updatedArtist != null) {
				messageProducerService.sendMessage("artists-topic",
						"In updateArtistAlbumName, updating artist name " + oldAlbumName + " to " + newAlbumName);
				return ResponseEntity.ok(updatedArtist);
			} else {
				MultiValueMap<String, String> headers = new HttpHeaders();
				headers.add("Error message", "Failed to update artist for id " + id);
				messageProducerService.sendMessage("artists-topic",
						"In updateArtistAlbumName, Failed to update artist for id " + id);
				return new ResponseEntity<Artist>(headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			MultiValueMap<String, String> headers = new HttpHeaders();
			headers.add("Error message", "Failed to find existing artist for id " + id);
			messageProducerService.sendMessage("artists-topic",
					"In updateArtistAlbumName, Failed to find existing artist for id " + id);
			return new ResponseEntity<Artist>(headers, HttpStatus.NO_CONTENT);
		}
	}
}
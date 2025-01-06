package com.ltd.coders.software.artist.and.albums.database.artist.with.albums.and.tracks;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@Tag(name = "Show Albums & Tracks For Artist")
@Validated
@RestController
@RequestMapping("v1/artist/service")
public class ShowAllAlbumsAndTracksForArtistController {

	private static final Logger log = LogManager.getLogger(ShowAllAlbumsAndTracksForArtistController.class);
	private IShowAllAlbumsAndTracksForArtistService readAllArtistsAndAlbumsService;
	private MessageProducerService messageProducerService;
	
	public ShowAllAlbumsAndTracksForArtistController(IShowAllAlbumsAndTracksForArtistService readAllArtistsAndAlbumsService,
			MessageProducerService messageProducerService) {
		this.readAllArtistsAndAlbumsService = readAllArtistsAndAlbumsService;
		this.messageProducerService = messageProducerService;
	}

	@Operation
	@GetMapping("/artist/album-names")
	public ResponseEntity<List<String>> showAllAlbumNamesForArtist(
			@RequestParam("artistName") @NotBlank(message = "artist name cannot be null or empty") String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistController.showAllAlbumsForArtistName()");
		List<String> artistAlbums = readAllArtistsAndAlbumsService.getAllAlbumsForArtist(artistName);
		if(artistAlbums != null) {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumNamesForArtist for artist "+artistName+" album names returned = "+artistAlbums.size());
			return ResponseEntity.ok(artistAlbums);
		}else {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumNamesForArtist for artist "+artistName+" no artists found = ");
			return ResponseEntity.noContent().build();
		}
	}
	
	@Operation
	@GetMapping("/artist/albums/tracks")
	public ResponseEntity<Artist> showAllAlbumsAndTracksForArtist(
			@RequestParam("artistName") @NotBlank(message = "artist name cannot be null or empty") String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistController.showAllAlbumsAndTracksForArtist() " + artistName);
		Artist artist = readAllArtistsAndAlbumsService.getAllAlbumsAndTracksForArtist(artistName);
		if (artist != null) {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumsAndTracksForArtist for artist "+artistName+" albums returned = "+artist.getAlbums().size());
			return ResponseEntity.ok(artist);
		} else {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumsAndTracksForArtist for artist "+artistName+" no albums returned");
			return ResponseEntity.noContent().build();
		}
	}

	@Operation
	@GetMapping("/artist/albums/tracks/query")
	public ResponseEntity<Artist> showAllAlbumsAndTracksForArtistQuery(
			@RequestParam("artistName") @NotBlank(message = "artist name cannot be null or empty") String artistName) {
		log.info("IN ShowAllAlbumsAndTracksForArtistController.showAllAlbumsAndTracksForArtistQuery()");
		Artist artist = readAllArtistsAndAlbumsService.getAllAlbumsAndTracksForArtistQuery(artistName);
		if(artist != null) {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumsAndTracksForArtistQuery for artist "+artistName+" albums returned = "+artist.getAlbums().size());
			return ResponseEntity.ok(artist);
		} else {
			messageProducerService.sendMessage("artists-topic", "In showAllAlbumsAndTracksForArtistQuery for artist "+artistName+" no albums returned");
			return ResponseEntity.noContent().build();
		}
	}
}
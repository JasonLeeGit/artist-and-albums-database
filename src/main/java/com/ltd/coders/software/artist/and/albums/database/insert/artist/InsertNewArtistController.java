package com.ltd.coders.software.artist.and.albums.database.insert.artist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.dto.ArtistRequestDto;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.exception.ArtistExistsException;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Insert New Artist")
@Validated
@RestController
@RequestMapping("v1/artist/service")
public class InsertNewArtistController {

	private static final Logger log = LogManager.getLogger(InsertNewArtistController.class);
	private IInsertNewArtistService insertNewArtistService;
	private MessageProducerService messageProducerService;
	
	public InsertNewArtistController(IInsertNewArtistService insertNewArtistService,
			MessageProducerService messageProducerService) {
		this.insertNewArtistService = insertNewArtistService;
		this.messageProducerService = messageProducerService;
	}

	@Operation
	@PostMapping("/artist")
	public ResponseEntity<Artist> insertArtist(@Valid @RequestBody ArtistRequestDto artistRequestDto)
			throws ArtistExistsException {
		log.info("InsertNewArtistController.insertArtist()");
		Artist insertedArtist = insertNewArtistService.insertArtist(artistRequestDto.toArtist());
		if (insertedArtist != null) {
			messageProducerService.sendMessage("artists-topic", "In insertArtist, inserting valid artist");
			return ResponseEntity.ok(insertedArtist);
		} else {
		 	MultiValueMap<String, String> headers = new HttpHeaders();
		 	headers.add("Error message", "Error failed to insert new artist");
		 	messageProducerService.sendMessage("artists-topic", "Error failed to insert new artist, artist invalid");
		 	return new ResponseEntity<Artist>(headers, HttpStatus.BAD_REQUEST);
		}
	}
}
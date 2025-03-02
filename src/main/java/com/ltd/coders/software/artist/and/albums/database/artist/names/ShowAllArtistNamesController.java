package com.ltd.coders.software.artist.and.albums.database.artist.names;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Show All Artist Names")
@RestController
@RequestMapping("v1/artist/service")
public class ShowAllArtistNamesController {

	private static final Logger log = LogManager.getLogger(ShowAllArtistNamesController.class);
	private IShowAllArtistNamesService readAllArtistNamesService;
	private MessageProducerService messageProducerService;

	public ShowAllArtistNamesController(IShowAllArtistNamesService readAllArtistNamesService,
			MessageProducerService messageProducerService) {
		this.readAllArtistNamesService = readAllArtistNamesService;
		this.messageProducerService = messageProducerService;
	}


	@Operation
	@GetMapping("/artists/names")
	public ResponseEntity<List<String>> showAllArtistNames() {
		log.info("ShowAllArtistNamesController.showAllArtistNames()");
		List<String> artists = readAllArtistNamesService.getAllArtistNames();
			
		if (artists != null) {
			messageProducerService.sendMessage("artists-topic","In showAllArtistNames, "+artists.toString());
			return ResponseEntity.ok(artists);
		} else {
			messageProducerService.sendMessage("artists-topic", "In showAllArtistNames no artists found");
			return ResponseEntity.noContent().build();
		}
	}
}
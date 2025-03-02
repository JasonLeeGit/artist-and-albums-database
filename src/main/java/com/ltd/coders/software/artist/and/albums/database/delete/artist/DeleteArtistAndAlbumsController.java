package com.ltd.coders.software.artist.and.albums.database.delete.artist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Tag(name = "Delete Artist & Albums")
@Validated
@RestController
@RequestMapping("/v1/artist/service")
public class DeleteArtistAndAlbumsController {

	private static final Logger log = LogManager.getLogger(DeleteArtistAndAlbumsController.class);
	private IDeleteArtistAndAlbumsService deleteArtistService;
	private MessageProducerService messageProducerService;
	
	public DeleteArtistAndAlbumsController(IDeleteArtistAndAlbumsService deleteArtistService,
			MessageProducerService messageProducerService) {
		this.deleteArtistService = deleteArtistService;
		this.messageProducerService = messageProducerService;
	}

	@Operation
    @DeleteMapping("/artist/artist-id")
	public void deleteArtistById(@RequestParam("id") @Positive(message = "id must be greater than zero") int id) {
		log.info("IN DeleteArtistAndAlbumsController.deleteArtistById()");
		messageProducerService.sendMessage("artists-topic", "In deleteArtistById ID = "+ id);
		deleteArtistService.deleteById(id);
	}

	@Operation
	@DeleteMapping("/artist/artist-name")
	public void deleteArtistByArtistName(
			@RequestParam("artistName") @NotBlank(message = "artist name cannot be null or empty") String artistName) {
		log.info("IN DeleteArtistAndAlbumsController.deleteArtistByArtistName()");
		messageProducerService.sendMessage("artists-topic", "In deleteArtistByArtistName artists name = "+ artistName);
		deleteArtistService.deleteByArtistName(artistName);
	}
	
}

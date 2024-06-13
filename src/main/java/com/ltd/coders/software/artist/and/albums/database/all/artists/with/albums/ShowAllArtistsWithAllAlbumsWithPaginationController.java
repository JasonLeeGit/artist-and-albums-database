package com.ltd.coders.software.artist.and.albums.database.all.artists.with.albums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@Tag(name = "Show All Artists and Albums")
@RequestMapping("/v1/artist/service")
public class ShowAllArtistsWithAllAlbumsWithPaginationController {

	private static final Logger log = LogManager.getLogger(ShowAllArtistsWithAllAlbumsWithPaginationController.class);
	
	public ShowAllArtistsWithAllAlbumsWithPaginationController(
			IShowAllArtistsWithAllAlbumsWithPaginationService readAllArtistsWithAllAlbumsService) {
		this.readAllArtistsWithAllAlbumsService = readAllArtistsWithAllAlbumsService;
	}
	
	@Autowired
	private MessageProducerService messageProducerService;
	
	public IShowAllArtistsWithAllAlbumsWithPaginationService readAllArtistsWithAllAlbumsService;

	@Operation // offset = start number, pageSize how many artists to return per page
	@GetMapping("/artists/albums/tracks/pagination")
	public ResponseEntity<Page<Artist>> showAllArtistsWithAllAlbumsAndTrackWithPagination(
			@RequestParam(required = true, defaultValue = "0") @Min(value = 0) @Max(value = 10) int offSet,
			@RequestParam(required = true, defaultValue = "1") @Min(value = 1) @Max(value = 10) int pageSize) {
		
		log.error("ShowAllArtistsWithAllAlbumsController.showAllArtistsWithAllAlbumsAndTrack()");
		Page<Artist> artists = readAllArtistsWithAllAlbumsService.getAllArtistsWithPagination(offSet, pageSize);
		
		if (artists != null) {
			messageProducerService.sendMessage("artists-topic", "In showAllArtistsWithAllAlbumsAndTrackWithPagination offset =  "+offSet
					+" pageSize = "+ pageSize +" Number of artists returned = "+artists.getSize());
			return ResponseEntity.ok(artists);
		} else {
			messageProducerService.sendMessage("artists-topic", "In showAllArtistsWithAllAlbumsAndTrackWithPagination offset =  "+offSet
					+" pageSize = "+ pageSize +" No artists returned" );
			return ResponseEntity.noContent().build();
		}
	}
}
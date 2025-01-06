package com.ltd.coders.software.artist.and.albums.database.initialize;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.artist.and.albums.database.kafka.MessageProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Used to populate a MySQL database by reading files in from external HDD will only run if database is empty
 */

@Tag(name = "Initialize Database")
@RestController
@RequestMapping("v1/artist/service")
public class InitDataBaseController {

	private static final Logger log = LogManager.getLogger(InitDataBaseController.class);
	@Autowired
	private IReadDirectoryFromHardDriveAndReturnDataService readDataService;
	@Autowired
	private IWriteDataToDatabaseService writeDataService;
	@Autowired
	private MessageProducerService messageProducerService;
	
	@Operation
	@GetMapping("/initialize/database")
	public void loadTableData() {

		if (writeDataService.isRepositoryPopulated() <= 0) {
			log.error("Database Empty Proceeding with reading and writing records");
			messageProducerService.sendMessage("artists-topic", "In loadTableData Database Empty Proceeding with reading and writing records");
			readDataService.directoryList().forEach(artist -> writeDataService.saveAndFlush(artist));
		} else {
			messageProducerService.sendMessage("artists-topic", "In loadTableData Database already populated no new records inserted");
			log.error("Database already populated no new records inserted");
		}
	}
}

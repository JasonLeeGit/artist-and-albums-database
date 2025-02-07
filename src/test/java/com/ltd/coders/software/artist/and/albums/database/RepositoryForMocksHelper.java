package com.ltd.coders.software.artist.and.albums.database;

import java.util.List;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Artist;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

@ActiveProfiles("test")
@Testcontainers
public abstract class RepositoryForMocksHelper {
	
	protected Artist artist;
	protected Album albumOne;
	protected Album albumTwo;
	protected Album albumThree;
	protected Album albumFour;
	protected List<String> artistNameList;
	protected List<Artist> artistList;
	protected List<Album> albumsList;
	protected List<Track> trackList;
	protected static final String BITRATE = "320";
	protected static final String ALBUM_ONE = "albumNameOne";
	protected static final String ALBUM_TWO = "albumNameTwo";
	protected static final String ALBUM_THREE = "albumNameThree";
	protected static final String ALBUM_FOUR = "albumNameFour";
	protected static final String ARTIST_NAME_ONE = "artistNameOne";
	protected static final String ARTIST_NAME_TWO = "artistNameTwo";
	protected static final String ARTIST_NAME_THREE = "artistNameThree";
	protected static final String ARTIST_NAME_FOUR = "artistNameFour";
	
	@Container
	@ServiceConnection
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
}

package com.ltd.coders.software.artist.and.albums.database.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Component;

import com.ltd.coders.software.artist.and.albums.database.entity.Album;
import com.ltd.coders.software.artist.and.albums.database.entity.Track;

@Component
public class AlbumTrackDetails {

	private static final Logger log = LogManager.getLogger(AlbumTrackDetails.class);	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public Album addAlbumDetails(Path path, String artistName, String albumName) {
		
		log.info("FindFileTagDetailsHelper.addAlbumDetails()");
		
		Album album = new Album();
		List<Track> tracks = new ArrayList<>();
		
		try {		
			Files.walkFileTree(Paths.get(path.toUri()), new SimpleFileVisitor<>() {			
				@Override
			    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
			        if (isValidForJTagger(filePath.toString()) && filePath.toFile().canRead()) {
			            File file = new File(filePath.toString());
			        	try {
			        		AudioFile musicTrack = AudioFileIO.read(file);
			        			if(isValidForJTagger(musicTrack.toString())) {
					        		Tag tag = musicTrack.getTag();
					        		
					        		String trackLength = "";
					        		if(musicTrack.getAudioHeader().getPreciseTrackLength() > 0) {
					        			trackLength = df.format(musicTrack.getAudioHeader().getPreciseTrackLength()/60);
					        		}
					        		
					        		if(tag != null) {
					        			Track track = Track.builder()
					        					.artistName(artistName)
					        					.albumName(albumName)
					        					.title(tag.getFirst(FieldKey.TITLE))
					        					.trackNumber(tag.getFirst(FieldKey.TRACK))
					        					.genre(tag.getFirst(FieldKey.GENRE))
					        					.recordLabel(tag.getFirst(FieldKey.RECORD_LABEL))
					        					.releaseYear(tag.getFirst(FieldKey.YEAR))
					        					.fileFormat(musicTrack.getAudioHeader().getFormat())
					        					.bitRate(musicTrack.getAudioHeader().getBitRate())
					        					.trackLength(trackLength)
					        					.isVariableBitRate(musicTrack.getAudioHeader().isVariableBitRate())
					        					.build();
					        			
						        		tracks.add(track);
					        		}		 
			        			}
			        	} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			        		e.printStackTrace();
			        	}
			    }
			        album.setTracks(tracks);
			        return FileVisitResult.CONTINUE;
			    }
				
			    @Override
			    public FileVisitResult visitFileFailed(Path file, IOException exc) {
			       log.error("Failed reading \"" + file.toString() + "\" -- skipped file" + exc);			        
			       return FileVisitResult.CONTINUE;
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return album;
	}
	
	public boolean isValidForJTagger(String value) {
		 return     !value.contains(".jpg") && !value.contains(".jpeg") 
				 && !value.contains(".JPG") && !value.contains(".db")
				 && !value.contains(".ini") && !value.contains("iTunes")
				 && !value.contains(".mpc") && !value.contains(".pdf")
				 && !value.contains(".avi") && !value.contains(".txt")
				 && !value.contains(".bmp") && !value.contains(".url")
				 && !value.contains(".png") && !value.contains(".gif")
				 && !value.contains(".mpg") && !value.contains(".mpeg")
				 && !value.contains(".m3u") && !value.contains(".MP3");
	}
}
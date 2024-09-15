#place in C:\workspace\ArtistAndAlbumsMySQL
# Pull in java 17 from docker hub
FROM openjdk:17-jdk-alpine

# Copy built jar from build\libs directory over to spring-boot-artist-albums-docker image
COPY build/libs/*.jar artist_and_album_mysql.jar

# Expose port 8080
EXPOSE 8080

# set the startup command to execute the jar
CMD ["java", "-jar", "/artist_and_album_mysql.jar"]
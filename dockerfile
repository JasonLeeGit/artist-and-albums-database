# Place this file in C:\workspace\artist-and-albums-database,
# Goto C:\artist-and-albums-database\build\libs: 			delete any old jars
# Goto C:\artist-and-albums-database:			 			gradle build -x test
# Goto C:\artist-and-albums-database\build\libs:		 	rename the jar file to artist_and_albums_msysql
# Goto C:\artist-and-albums-database\build\libs: 			docker build -t artist_and_albums_msysql .
# Goto C:\artist-and-albums-database:		  				docker-compose up -d (to start required MySQL and Kafka containers)
# Goto C:\artist-and-albums-database\build\libs: 			docker run -d -p 8080:8080 artist_and_albums_msysql:latest (to start App container)


# Pull in java 17 from docker hub
FROM openjdk:17-jdk-alpine

# Copy built jar from build\libs directory over to spring-boot-artist-albums-docker image/container
COPY build/libs/*.jar artist_and_album_mysql.jar

# Expose port 8080
EXPOSE 8080

# set the startup command to execute the jar
CMD ["java", "-jar", "/artist_and_album_mysql.jar"]
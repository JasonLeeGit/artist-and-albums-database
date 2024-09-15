# Place this file in C:\workspace\artist-and-albums-database,
# Goto C:\artist-and-albums-database\build\libs: 	delete any old jars
# Goto C:\artist-and-albums-database and run: 		gradle build -x test
# Goto C:\artist-and-albums-database\build\libs: 	rename the jar file to artist_and_albums_msysql
# Goto C:\artist-and-albums-database and run: 		docker build -t artist_and_albums_msysql . 
# When finished running goto Docker Desktop to start Application running [set port to 8080]

# Pull in java 17 from docker hub
FROM openjdk:17-jdk-alpine

# Copy built jar from build\libs directory over to spring-boot-artist-albums-docker image
COPY build/libs/*.jar artist_and_album_mysql.jar

# Expose port 8080
EXPOSE 8080

# set the startup command to execute the jar
CMD ["java", "-jar", "/artist_and_album_mysql.jar"]
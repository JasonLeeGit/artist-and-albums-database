# artist-and-albums-database
Spring Boot Rest application for searching and viewing artists and albums

To Run,

cd to your project resource folder,

cd C:\workspace\artist-and-albums-database\src\main\resources

open terminal or cmd prompt and run,

docker-compose up -d

This will start the MySQL and Kafka containers

Start the artist-and-albums-database spring boot application

Open a browser and goto,

http://localhost:8080/swagger-ui/swagger-ui/index.html
 
![image](https://github.com/user-attachments/assets/65f808c0-d48a-46f7-890c-f0053623616f)

![image](https://github.com/user-attachments/assets/cceffad3-4096-440a-9d74-b090b76fe632)

To view Kafka Topic and messages goto,

http://localhost:8090/ui/clusters/local/all-topics/artists-topic/messages?keySerde=String&valueSerde=String&limit=100

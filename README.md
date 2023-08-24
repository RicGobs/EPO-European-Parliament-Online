# European-Referendum-Online

## How to create the Dockerfile and run the container

- Dokerfile for Spring Boot Application: [reference](https://spring.io/guides/topicals/spring-boot-docker/) (during the creation of the jar file, write package as Goals)


## How to launch the Application (and possible errors)

```
#LAUNCH CODE

#to launch and build the application 
sudo docker compose up --build 

-------

#POSSIBLE ERRORS

#to see which ports are used by the pc
sudo lsof -i -P -n | grep LISTEN

#to stop the service on ports that you need 

#usually for rabbitMQ
sudo fuser -k 5672/tcp 

#usually for MySQL
sudo fuser -k 5432/tcp

#to eliminate some old containers that can create conflicts
sudo docker container rm /rabbitmq
sudo docker container rm /sender
sudo docker container rm /receiver
```

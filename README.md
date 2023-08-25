# European-Referendum-Online

## How to launch the Application (and possible errors)

### LAUNCH CODE
```
#to launch and build the application 
sudo docker compose up 
```

### POSSIBLE ERRORS
#### To see which ports are used by the pc
```
sudo lsof -i -P -n | grep LISTEN
```
#### To stop the service on ports that you need 
```
#usually for rabbitMQ
sudo fuser -k 5672/tcp 

#usually for MySQL
sudo fuser -k 5432/tcp
```

#### To eliminate some old containers that can create conflicts

```
sudo docker container rm /rabbitmq
sudo docker container rm /sender
sudo docker container rm /receiver
sudo docker container rm /web
sudo docker container rm /rest
```
Never use this command with /psql (you will delete this container and lose all your data)

## Info about Ports and Services

EPO-ITA 
* web-ita : http://localhost:8080/
* rest-ita : http://localhost:8081/referendumideaproposals
* receiver-ita : http://localhost:8082/message_here
* sender-ita, rabbitmq-ita, psql-ita : not visible

EPO-FRA 
* web-fra : http://localhost:8084/
* rest-fra : http://localhost:8085/referendumideaproposals
* receiver-fra : http://localhost:8086/message_here
* sender-fra, rabbitmq-fra, psql-fra : not visible

EPO-GERM 
* web-ger : http://localhost:8088/
* rest-ger : http://localhost:8089/referendumideaproposals
* receiver-ger : http://localhost:9000/message_here
* sender-ger, rabbitmq-ger, psql-ger : not visible

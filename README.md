# European-Referendum-Online

## How to launch the Application (and possible errors)

### LAUNCH CODE

##### To launch and build the application 
```
sudo docker compose -f docker-compose-all.yaml up --build
```

##### Wait until rabbitmq container ends the initialization
```
sudo docker compose -f docker-compose-ita.yaml up --build
sudo docker compose -f docker-compose-fra.yaml up --build
sudo docker compose -f docker-compose-ger.yaml up --build
```

##### Alternative(for linux users): 
```
chmod u+x command_start.sh 
./command_start.sh 
```
### POSSIBLE ERRORS

##### To see which ports are used by the pc

```
sudo lsof -i -P -n | grep LISTEN
```

#### To stop the service on ports that you need

usually for rabbitMQ
```
sudo fuser -k 5672/tcp 
```

usually for MySQL
```
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

## To clean your system from all containers
With this command you eliminate all data that you store in Docker, if you have other containers and other images (different from this project), DO NOT USE this command.
```
sudo docker system prune --all --force
```

## Command for the POST

```
#Post request from terminal: 
curl -X POST localhost:8082/europeanReferendumBroadcast -H "Content-type:application/json" -d "{\"title\": \"Samwise Gamgee\"}"

curl -X POST localhost:8086/europeanReferendumBroadcast -H "Content-type:application/json" -d "{\"title\": \"Samwise Gamgee\"}"

curl -X POST localhost:8090/europeanReferendumBroadcast -H "Content-type:application/json" -d "{\"title\": \"Samwise Gamgee\"}"
```
 
## Info about Ports and Services

RABBIT_MQ

* port 5672
* port 15672

EPO-ITA

* web-ita : http://localhost:8080/
* rest-ita : http://localhost:8081/referendumideaproposals
* broadcast-ita : http://localhost:8082/
* psql-ita : not visible

EPO-FRA

* web-fra : http://localhost:8084/
* rest-fra : http://localhost:8085/referendumideaproposals
* broadcast-fra : http://localhost:8086/
* psql-fra : not visible

EPO-GERM

* web-ger : http://localhost:8088/
* rest-ger : http://localhost:8089/referendumideaproposals
* broadcast-ger : http://localhost:8090/
* psql-ger : not visible

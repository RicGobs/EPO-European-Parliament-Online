#
# Build stage
#
FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/web-0.0.1-SNAPSHOT.jar /usr/local/lib/app-1.0.0.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app-1.0.0.jar"]
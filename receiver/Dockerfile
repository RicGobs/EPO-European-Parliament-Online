FROM maven:3.5.4-jdk-11
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests
EXPOSE 8083
CMD ["mvn", "spring-boot:run"]

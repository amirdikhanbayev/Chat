FROM maven:3.8.4-jdk-11 AS build
COPY pom.xml .
COPY src/ ./src/
RUN mvn package -DskipTests
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /target/BackChat-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
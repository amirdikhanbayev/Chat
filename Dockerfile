FROM eclipse-temurin:11-alpine
WORKDIR /app
COPY ./target/*.jar /application.jar
CMD ["java", "-jar", "/application.jar"]
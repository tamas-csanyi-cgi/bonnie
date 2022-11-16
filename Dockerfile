FROM eclipse-temurin:18-jre
COPY ./starter-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8082
CMD ["java", "-jar",  "/app.jar", "--spring.profiles.active=devserver"]
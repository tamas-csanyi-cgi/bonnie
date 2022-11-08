FROM eclipse-temurin:18-jre
COPY /starter/targer/starter-1.0-SNAPSHOT.jar /app.jar
RUN java -jar /app.jar --Dspring.profiles.active=devservert
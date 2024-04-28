FROM gradle:8.7.0-jdk17
COPY ./build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
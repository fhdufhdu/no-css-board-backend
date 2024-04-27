FROM openjdk:17-oracle
RUN microdnf install findutils
COPY . /spring

WORKDIR /spring
RUN ./gradlew clean && ./gradlew bootJar
RUN mv ./build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
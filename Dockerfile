FROM openjdk:11.0.9-slim

COPY ./build/libs/*.jar welper.jar
ENTRYPOINT ["java", "-jar","/welper.jar"]

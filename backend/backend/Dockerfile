FROM openjdk:18-alpine

ARG JAR_FILE=build/libs/*SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} backend.jar

ENTRYPOINT ["java","-jar","backend.jar"]

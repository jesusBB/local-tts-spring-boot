FROM openjdk:17-alpine

#FROM arm32v7/eclipse-temurin:17.0.9_9-jdk
#FROM arm32v7/maven:eclipse-temurin
#FROM maven:latest
#FROM arm32v7/eclipse-temurin

#FROM eclipse-temurin:17-jdk-alpine

#RUN apk --no-cache add iputils


#VOLUME /tmp

#WORKDIR .
WORKDIR /app

#ARG JAR_FILE=target/*.jar
#ADD ${JAR_FILE} app.jar
#RUN echo ${PWD}
#RUN ls /home/gitlab-runner/builds/nCPmK5Rz/0/jesuli/restful-web-services/target/*.jar
#COPY . .
COPY target/local-tts-spring-boot-*.jar /app/local-tts-spring-boot.jar

ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY

EXPOSE 8080

ENTRYPOINT ["java","-jar","local-tts-spring-boot.jar"]
#ENTRYPOINT ["mvn","spring-boot:run"]


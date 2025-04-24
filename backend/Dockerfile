FROM gradle:8.13-jdk17 AS build

ARG APP_NAME=devops
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17-jdk-slim as deploy
WORKDIR /home/aviaback/app
COPY --from=build /home/gradle/src/build/libs/backend-0.0.1-SNAPSHOT.jar ./app.jar

CMD java -jar app.jar
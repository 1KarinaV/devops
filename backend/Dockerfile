FROM gradle:8.13-jdk17 AS build

ARG APP_NAME=devops
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17-jdk-slim as deploy
WORKDIR /home/aviaback/app
COPY --from=build /home/gradle/src/build/libs/backend-latest.jar ./app.jar

RUN ls
CMD java -jar app.jar
FROM gradle:8.13-jdk17 AS build

ARG APP_NAME=devops
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

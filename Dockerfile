FROM openjdk:17-slim

WORKDIR /app

ARG GIT_COMMIT = unspecified
ENV JAVA_OPTS=""
ENV JAVA_OPTS_DD="-Ddd.version=$GIT_COMMIT"

RUN groupadd spring && useradd -g spring spring
RUN chown spring /app
USER spring:spring

COPY target/*.jar team-gof.jar

EXPOSE 8081
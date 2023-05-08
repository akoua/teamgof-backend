FROM openjdk:17-slim

WORKDIR /app

ARG GIT_COMMIT=unspecified
ENV JAVA_OPTS=""

RUN groupadd spring && useradd -g spring spring
RUN chown spring /app
USER spring:spring

COPY target/*.jar team-gof.jar

EXPOSE 8081

CMD ["java", "-jar", "team-gof.jar"]
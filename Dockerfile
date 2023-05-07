FROM clipse-temurin:20
CMD mkdir /jar
COPY target/*.jar /jar/
EXPOSE 8081
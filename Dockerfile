FROM amazoncorretto:17-alpine-jdk
MAINTAINER Proyecto-jardin
COPY target/empresa-0.0.1-SNAPSHOT.jar empresa-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/empresa-app.jar"]

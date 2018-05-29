FROM java:8

COPY target/gameoflife-1.0.1.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar", "/app.jar"]
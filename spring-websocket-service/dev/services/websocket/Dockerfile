## Java
FROM adoptopenjdk/openjdk11:alpine

## Add created JAR to image
COPY *.jar app.jar

# Default command to be executed when the container is deployed
ENTRYPOINT ["sh", "app-start.sh"]
# use base image with java version
FROM openjdk:17-jdk-slim

# set workdir
WORKDIR /app

# Copy app jar into container
COPY target/url-shortner app.jar

# expose port
EXPOSE 8080

# Run application
CMD ["java", "-jar", "app.jar"]
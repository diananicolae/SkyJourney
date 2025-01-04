# Use Azul Zulu OpenJDK 17 as the base image
FROM azul/zulu-openjdk:17

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/sky-journey-core-1.jar

# Add the application's jar to the container
ADD ${JAR_FILE} sky-journey-core-1.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sky-journey-core-1.jar"]

# Use Azul Zulu OpenJDK 17 as the base image
FROM azul/zulu-openjdk:17

# Volume pointing to /tmp
VOLUME /tmp

# Expose port 8081
EXPOSE 8081

# Add the application's jar file
ARG JAR_FILE=build/libs/sky-journey-payment-1.jar

# Add the jar file to the container
ADD ${JAR_FILE} sky-journey-payment-1.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sky-journey-payment-1.jar"]

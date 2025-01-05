# Use the Amazon Corretto JDK 17 as the base image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the build output from Gradle
COPY ./subproject/boot/build/libs/boot.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Define environment variables if needed
ENV JAVA_OPTS=""

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
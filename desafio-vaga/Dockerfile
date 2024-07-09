# Use a base image with Java 17 (compatible with your project's Java version)
FROM openjdk:17-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven executable wrapper from the host to the container
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml and other necessary files
COPY pom.xml .

# Copy the source code files from the host to the container
COPY src src

# Install all dependencies and package the application
RUN ./mvnw install -DskipTests

# Start a new stage from scratch for a smaller final image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy only the built jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]

# Use a lightweight Java 17 image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the compiled jar from the target directory
COPY target/*.jar app.jar

# Expose the port Spring Boot uses
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
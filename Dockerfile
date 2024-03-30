FROM openjdk:17-jdk-slim
WORKDIR /app
COPY backend/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
EXPOSE 8443
CMD ["java", "-jar", "demo.jar"]

# Go to backend/src/main/resources/application.properties and change the url if you want to make a new docker image.
# If you have already a docker image, go to the third command, compile docker container.

# Generate JAR file (go to pom directory): 
# mvn clean package -DskipTests

# Generate docker image: 
# docker build -t gualapop2024 .

# Compile docker container with the image: 
# docker run -p 8443:8443 --name gualapop gualapop2024


FROM maven as builder
COPY . /code/
WORKDIR /code/backend
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /code/backend/target/*.jar /usr/src/
WORKDIR /usr/src
EXPOSE 8443
CMD [ "java", "-jar", "gualapop-0.0.1-SNAPSHOT.jar" ]

# Go to backend/src/main/resources/application.properties and change the url if you want to make a new docker image.
# If you have already a docker image, go to the third command, compile docker container.

# Generate JAR file (go to pom directory): 
# mvn clean package -DskipTests

# Generate docker image: 
# docker build -t gualapop .

# Compile docker container with the image: 
# docker run -p 8443:8443 --name gualapop gualapop2024


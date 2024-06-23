# Etapa 1: Construir la aplicación Angular
FROM node:20.12.2 as angular
WORKDIR /app/frontend/fase4
COPY frontend/fase4/package*.json ./
RUN npm install --legacy-peer-deps
RUN npm audit fix --legacy-peer-deps
COPY frontend/fase4/. .
RUN npm run build

# Etapa 2: Empaquetar la aplicación de Spring Boot con Maven y OpenJDK
FROM maven as builder
WORKDIR /app
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Etapa final: Crear la imagen Docker final con la aplicación compilada
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
COPY --from=angular /app/frontend/fase4/dist/fase4 /app/backend/src/main/resources/static/new/
EXPOSE 8443
CMD ["java", "-jar", "app.jar"]


#FROM node:20.12.2 AS angular
#WORKDIR /code
#COPY frontend/fase4/* /code/
#RUN npm install --legacy-peer-deps
#RUN npm audit fix --legacy-peer-deps
#COPY frontend/fase4/src /code/src
#RUN npm run build 
#
#FROM maven as builder
#WORKDIR /project
#COPY backend/pom.xml /project/
#COPY backend/src /project/src
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /usr/src
#COPY --from=builder /project/backend/target/*.jar /usr/src/
#COPY --from=angular /code/frontend/dist/fase4/browser/ /project/backend/src/main/resources/static/new/
#EXPOSE 8443
#CMD [ "java", "-jar", "gualapop-0.0.1-SNAPSHOT.jar" ]

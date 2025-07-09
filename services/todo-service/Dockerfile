# STAGE 1: build con Maven
FROM maven:3.9.9-eclipse-temurin-17-noble AS build
WORKDIR /app
COPY pom.xml . 
COPY src ./src
RUN mvn clean package -DskipTests


# STAGE 2: runtime minimale
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copia il JAR generato
COPY --from=build /app/target/*.jar app.jar


# Espone la porta di Spring Boot
EXPOSE 8080

# Comando per avviare l'applicazione
CMD ["java", "-jar", "app.jar"]

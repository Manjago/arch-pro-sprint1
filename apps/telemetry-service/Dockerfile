# --- Этап 1: Сборка (Builder) ---
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests


# --- Этап 2: Исполнение (Runtime) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/telemetry-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 7070 

CMD ["java", "-jar", "app.jar"]

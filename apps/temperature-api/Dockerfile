# --- Этап 1: Сборка (Builder) ---
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Сначала копируем только pom.xml
COPY pom.xml .
# Скачиваем зависимости (и плагины Maven). Этот слой будет кэшироваться.
RUN mvn dependency:go-offline

# Теперь копируем исходники
COPY src ./src

# Собираем приложение
RUN mvn package -DskipTests


# --- Этап 2: Исполнение (Runtime) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/temperatureapi-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]

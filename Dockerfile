# === Этап 1: Сборка ===
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Копируем pom.xml для кэширования зависимостей
COPY pom.xml .

# Копируем исходный код
COPY src ./src

# Собираем JAR без запуска тестов
RUN mvn clean package -DskipTests

# === Этап 2: Запуск ===
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем JAR из builder-этапа
# Убедитесь, что имя артефакта совпадает с тем, что генерирует Maven (обычно ${artifactId}-${version}.jar)
COPY --from=builder /app/target/FastDoor-*.jar ./app.jar

EXPOSE 8080

# Передаём профиль Spring через системное свойство
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
# Используем официальный образ с Java
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл jar в контейнер
COPY target/MrDverkin-0.0.1-SNAPSHOT.jar /app/MrDverkin-0.0.1-SNAPSHOT.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение с продакшн профилем
CMD ["java", "-jar","-Dspring.profiles.active=dev", "MrDverkin-0.0.1-SNAPSHOT.jar"]
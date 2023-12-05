FROM amazoncorretto:17.0.8

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /backend

# Копируем JAR файл приложения внутрь контейнера
COPY target/demo-0.0.1-SNAPSHOT.jar /backend/app.jar

# Указываем порт, который будет использоваться приложением
EXPOSE 8090

# Команда для запуска приложения
CMD sleep 3 && java -jar app.jar
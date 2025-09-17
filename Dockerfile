FROM maven:3.9-eclipse-temurin-19 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests \
    -Dmaven.compiler.source=19 \
    -Dmaven.compiler.target=19

#Финальная стадия
FROM eclipse-temurin:19-jdk-alpine
WORKDIR /app

# Создаём пользователя и нужные директории ДО переключения пользователя
RUN addgroup -S spring && adduser -S spring -G spring \
    && mkdir -p /app/filesExcel \
    && chown -R spring:spring /app

#Копируем JAR из стадии сборки
COPY --from=builder --chown=spring:spring /app/target/KursachRPS-0.0.1-SNAPSHOT.jar app.jar

#Переменные окружения
ENV SPRING_PROFILES_ACTIVE=docker \
    PDF_FILES_PATH=/app/filesExcel/ \
    PROJECT_PATH=/app \
    JAVA_OPTS="-Xmx512m -Xms256m -Dfile.encoding=UTF-8" \
    TZ=Europe/Moscow

#Открываем порт (Spring boot по умолчанию 8080)
EXPOSE 8080

USER spring
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]


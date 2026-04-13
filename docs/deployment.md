# Развертывание

## Контейнеры

Система разворачивается с использованием Docker Compose и включает:

- db — PostgreSQL
- backend — основной backend-сервис
- api — файловый сервис
- frontend — клиентское приложение

## Запуск

```bash
docker compose up --build
```
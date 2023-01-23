# product-service (ITBooks)
Сервис для управления каталогом книг интернет магазина ITBooks

**Стек: Spring Boot, Spring Cloud Config Client, Spring WebFlux, Spring Data JDBC, PostgreSQL, Flyway, JUnit, Mockito, Testcontainers, Gradle.**
## Описание
Проект представляет собой микросервис для управления каталогом книг системы приложений интернет магазина ITBooks. 
Сервис совершает CRUD операции над книгами. В сервисе настроен клиент для взаимодействия с сервером конфигурации, который загружает конфигурацию из внешнего репозитория.
Написаны модульные тесты с использованием JUnit, Mockito, библиотек Jakarta Validation, JacksonTester, интеграционные тесты с использованием Testcontainers и WebTestClient из проекта Spring WebFlux.
## Запуск
Для работы сервиса необходимо сначала развернуть и запустить базу данных PostgreSQL в контейнере Docker. Для этого необходимо на компьютере иметь запущенное приложение Docker. Далее создать папку на своем компьютере, скачать и положить в эту папку из [этого](https://github.com/ArtJDev/itbooks-deployment/tree/main/docker) репозитория скрит docker-compose.yml и папку postrgresql со скриптом init.sql. Далее открыть окно терминала, перейти в созданную папку со скриптом Docker и выполнить команду `docker-compose up -d itbooks-postgres`, Docker подтянет образ PostgreSQL, настроит необходимые базы данных и запустит их в контейнере "itbooks-postgres".
Запуск приложения осуществляется командой `./gradlew bootRun`.

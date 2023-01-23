# product-service (ITBooks)
Сервис для управления каталогом книг интернет магазина ITBooks

**Стек: Spring Boot, Spring Cloud Config Client, Spring WebFlux, Spring Data JDBC, PostgreSQL, Flyway, JUnit, Mockito, Testcontainers, Gradle.**
## Описание
Проект представляет собой микросервис для управления каталогом книг системы приложений интернет магазина ITBooks. 
Сервис совершает CRUD операции над книгами. В сервисе настроен клиент для взаимодействия с сервером конфигурации, который загружает конфигурацию из внешнего репозитория.
Написаны модульные тесты с использованием JUnit, Mockito, библиотек Jakarta Validation, JacksonTester, интеграционные тесты с использованием Testcontainers и WebTestClient из проекта Spring WebFlux.

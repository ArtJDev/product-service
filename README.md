# product-service
Сервис для управления катологом книг магазина ITBooks

**Стек: Spring Boot, Spring Cloud Config Client, Spring WebFlux, Spring Data JDBC, PostgreSQL, Flyway, JUnit, Mockito, Testcontainers, Gradle.**
## Описание
Проект представляет собой микросервис для управления каталогом книг системы приложений интернет магазина ITBooks. 
Сервис совершает CRUD операции над кингами. В сервисе настроен клиент для взаимодействия с сервером конфигурации, который загружает конфигурацию из внешнего репозитория.
Напсианы модульные тесты с использованием JUnit, Mockito, библитоек Jakarta Validation, JacksonTester, интеграционные тесты с использованием Testcontainers и WebTestClient из пректа Spring WebFlux.

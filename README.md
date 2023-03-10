# product-service (ITBooks)
Сервис для управления каталогом книг интернет магазина ITBooks

**Стек: Java 17, Spring Boot 3.0, Spring Cloud Config Client, Spring WebFlux, Spring Data JDBC, PostgreSQL, Flyway, JUnit, Mockito, Testcontainers, Gradle, Docker.**
## Описание
Проект представляет собой микросервис для управления каталогом книг системы приложений интернет магазина ITBooks. 
Сервис совершает CRUD операции над книгами. В сервисе настроен клиент для взаимодействия с сервером конфигурации, который загружает конфигурацию из внешнего репозитория.
Написаны модульные тесты с использованием JUnit, Mockito, библиотек Jakarta Validation, JacksonTester, интеграционные тесты с использованием Testcontainers и WebTestClient из проекта Spring WebFlux.
## Запуск
Для работы сервиса необходимо сначала развернуть и запустить базу данных PostgreSQL в контейнере Docker. Для этого необходимо на компьютере иметь запущенное приложение Docker. Далее создать папку на своем компьютере, скачать и положить в эту папку из [этого](https://github.com/ArtJDev/itbooks-deployment/tree/main/docker) репозитория скрипт docker-compose.yml и папку postrgresql со скриптом init.sql. Далее открыть окно терминала, перейти в созданную папку со скриптом Docker и выполнить команду `docker-compose up -d itbooks-postgres`, Docker подтянет образ PostgreSQL, настроит необходимые базы данных и запустит их в контейнере "itbooks-postgres".
Запуск приложения осуществляется командой `./gradlew bootRun`. При первом запуске в базу данных будут загружены тестовые данные.
Также возможен запуск приложения из контейнера Docker, для этого сначала нужно построить образ приложения `./gradlew bootBuildImage`, 
затем выполнить команду `docker-compose up -d product-service`.
## Спецификация REST API
| Endpoint | HTTP method | Request body | Status | Response body | Описание |
|------------------|--------|------|-----|--------|---------------------------------------------------|
| /books           |  GET   |      | 200 | Book[ ]|	Получить все книги из каталога |
| /books           |  POST  |	Book | 201 |  Book	|	Добавить новую книгу в каталог |
|				           |			  |			 | 422 |	  	  | Книга с таким артиклем уже существует |
| /books/{article} |  GET   |      | 200 |  Book  | Получить книгу по артиклю |
|									 |		    |      | 404 |        |	Книга с таким артиклем не существует |
| /books/{article} |  PUT   | Book | 200 |	Book  | Обновить книгу по артиклю |
|									 |        |      | 201 |	Book  | Создать книгу с артиклем, если такая не существует |
| /books/{article} | DELETE	|		   | 204 |        |	Удалить книгу по артиклю |
## Тестовые данные
Книги с артиклями 00100, 00101, 00102.

Json объект для POST, PUT запросов
{
    "author":"Автор",
    "title":"Название",
    "article":"12345",
    "price": 100.0
}
## Сервер конфигурации
Для тестирования загрузки конфигурации необходимо сначала запустить сервер конфигурации [config-service](https://github.com/ArtJDev/config-service) и 
упаковать приложение product-service в jar архив командой `./gradlew bootJar`. 

Запустить product-service с настройками из файла product-service.yml сохраненными в репозитории [config-repo](https://github.com/ArtJDev/config-repo):

Команда - `java -jar build/libs/product-service-0.0.1-SNAPSHOT.jar`, эндпоинт - `http://localhost:9001/`

Запустить product-service с настройками из файла product-service-prod.yml сохраненными в репозитории [config-repo](https://github.com/ArtJDev/config-repo):

Команда `java -jar build/libs/product-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`, эндпоинт - `http://localhost:9001/`

Если сервер конфигурации [config-service](https://github.com/ArtJDev/config-service) не работает или не доступен, сервис продолжит работать с настройками по умолчанию

В случае обновления настроек конфигурации в репозитории [config-repo](https://github.com/ArtJDev/config-repo), когда приложения product-service и config-service запущены необходимо сделать POST запрос на эндпоинт - `http://localhost:9001/actuator/refresh`, посмотреть новые настройки эндпоинт - `http://localhost:9001/`

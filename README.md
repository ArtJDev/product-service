# product-service (ITBooks)
Сервис для управления каталогом книг интернет магазина ITBooks

**Стек: ![Java](https://user-images.githubusercontent.com/98458226/214100040-5255dc76-61d2-41c5-ba33-c6de86c4f468.jpg)Java 17, ![Spring Boot](https://user-images.githubusercontent.com/98458226/214100131-059ec03d-f2f0-4590-b25b-fded090d6eb6.jpg)Spring Boot 3.0, ![Spring Cloud](https://user-images.githubusercontent.com/98458226/214100206-a57714a0-7ee1-4461-ad4a-6684a04cd50c.jpg)Spring Cloud Config Client, ![Spring WebFlux](https://user-images.githubusercontent.com/98458226/214100288-3ac39a6b-680c-4613-b374-0e4d7e4e6a9d.jpg)Spring WebFlux, ![Spring Data](https://user-images.githubusercontent.com/98458226/214100370-49a7e7f9-0610-49ba-9c07-e94ecac238d2.jpg)Spring Data JDBC, ![PostgreSQL](https://user-images.githubusercontent.com/98458226/214100451-9db1419c-683b-4818-b11a-6f19cc422570.jpg)PostgreSQL, 
![Flyway](https://user-images.githubusercontent.com/98458226/214100551-a4f5d982-ebaf-419e-921c-8efd9dfda693.jpg)Flyway, ![Junit](https://user-images.githubusercontent.com/98458226/214100614-257aa18f-c01c-4649-b8d1-1773c7ffc4b3.jpg)JUnit,![Mockito](https://user-images.githubusercontent.com/98458226/214100652-fd4ae2f9-a00d-4d97-bba5-d3cc6639a119.jpg)Mockito,![Testcontainers](https://user-images.githubusercontent.com/98458226/214100742-8a45e96a-4e50-4ebc-9968-b477f1b65427.jpg)Testcontainers, ![Gradle](https://user-images.githubusercontent.com/98458226/214100818-07f832f0-46fa-4ca1-bd7a-7dced36f13bc.jpg)Gradle.**
## Описание
Проект представляет собой микросервис для управления каталогом книг системы приложений интернет магазина ITBooks. 
Сервис совершает CRUD операции над книгами. В сервисе настроен клиент для взаимодействия с сервером конфигурации, который загружает конфигурацию из внешнего репозитория.
Написаны модульные тесты с использованием JUnit, Mockito, библиотек Jakarta Validation, JacksonTester, интеграционные тесты с использованием Testcontainers и WebTestClient из проекта Spring WebFlux.
## Запуск
Для работы сервиса необходимо сначала развернуть и запустить базу данных PostgreSQL в контейнере Docker. Для этого необходимо на компьютере иметь запущенное приложение Docker. Далее создать папку на своем компьютере, скачать и положить в эту папку из [этого](https://github.com/ArtJDev/itbooks-deployment/tree/main/docker) репозитория скрит docker-compose.yml и папку postrgresql со скриптом init.sql. Далее открыть окно терминала, перейти в созданную папку со скриптом Docker и выполнить команду `docker-compose up -d itbooks-postgres`, Docker подтянет образ PostgreSQL, настроит необходимые базы данных и запустит их в контейнере "itbooks-postgres".
Запуск приложения осуществляется командой `./gradlew bootRun`. При первом запуске в базу данных будут загружены тестовые данные.
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

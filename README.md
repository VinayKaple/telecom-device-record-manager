# telecom-device-record-manager
A Spring Boot REST API application for managing telecom devices* The application provides endpoints to create, retrieve, update, and delete device records and is designed following clean architecture, REST best practices, and testability in mind*

🚀 Features
-----------------------------------------------
* Create a new device

* Fetch all devices

* Fetch a device by ID

* Update a device partially (PATCH semantics)

* Delete a device

* PostgreSQL persistence (Dockerized)

* Global exception handling

* High unit test coverage (service layers)

🛠 Tech Stack
---------------------------------------------
* Java 21

* Spring Boot 3+

* Spring Data JPA

* PostgreSQL 16

* Hibernate

* Docker & Docker Compose

* JUnit 5 & Mockito

* Swagger / OpenAPI

## API Endpoints
### Base URL 
```http
/tdm-api/v1
```
### Create Device
```http
POST /devices
{
  "name": "iPhone 14",
  "brand": "Apple",
  "state": "AVAILABLE"
}
```
### Get all devices
```http
GET /devices
```
Optional query params: brand or state(AVAILABLE, IN_USE, INACTIVE)
```http
GET /devices?brand=Apple
GET /devices?state=AVAILABLE
```
### Get Device by ID
```http
GET /devices/{id}
```
### Update Device (Partial Update)
```http
PATCH /devices/{id}
{
  "brand": "Samsung"
}
```
❌ Updating a device in IN_USE state is not allowed*

### Delete Device
```http
DELETE /devices/{id}
```
❌ Deleting a device in IN_USE state is not allowed*

Error Handling
-----------------------------------------------------
The application uses a GlobalExceptionHandler to ensure consistent error responses across environments (local & Docker)*

Example error response:
```http
{
  "status": 500,
  "message": "Cannot update name or brand when device is in use*"
}
```
Testing
-------------------
Unit Tests

* Service layer tested using JUnit 5 and Mockito

```http
mvn test
```
Docker Setup
------------------
Dockerfile

* Multi-stage build

* Maven build → lightweight JRE runtime

* docker-compose*yml

Includes:

* API service

* PostgreSQL database

* Named volume for persistent DB storage
-----------------------------------
Start the Docker engine

At the root of the application folder do the following steps

```http
ls -l Dockerfile
docker compose config
docker compose build --no-cache
docker compose up
```
Access
--------------------------------
Application will be available at

```http
http://localhost:8080
```
Swagger URL

```http
http://localhost:8080/swagger-ui/index.html#/
```








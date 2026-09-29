# IoT Gateway Backend

A Java/Spring Boot backend for receiving device data through REST and Socket.IO, storing it in MySQL, and exposing it to authorized clients.

## Backend scope
- Device data, user accounts, and client/device API key endpoints
- Spring Security with JWT authentication
- REST APIs and a Socket.IO gateway for real-time communication
- JPA persistence and API documentation
- RestAssured/JUnit API tests under `src/test`

## Stack
Java 17, Spring Boot, Spring Data JPA, MySQL, Spring Security, JWT, Netty Socket.IO, RestAssured.

## Run locally
Install Java 17 and MySQL. Set the datasource URL and username in `src/main/resources/application.properties`, and provide `DB_PASSWORD` and `DEMO_USER_PASSWORD` as environment variables. Then run:

```bash
./mvnw spring-boot:run
```

The Socket.IO server uses the host and port configured in `application.properties`. Run the API tests with `./mvnw test`; they require the test database/configuration in the repository and a running MySQL instance.

## Review notes
This is a project/demo, not a managed production service. Do not reuse its example account or database setup in a deployment. Credentials formerly committed to this repository must be considered exposed and replaced wherever they were used.

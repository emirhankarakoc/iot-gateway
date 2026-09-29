# IoT Gateway Backend

This Java and Spring Boot app saves device data from REST requests in MySQL. Its Socket.IO server sends live messages between connected clients.

## What is in the code

- APIs for users, devices, data, and client/device API keys
- JWT login and access rules
- A Socket.IO server for live messages
- JPA database code and RestAssured/JUnit API tests

## Run locally

You need Java 17 and MySQL. Set your database URL and user in `src/main/resources/application.properties`. Set `DB_PASSWORD` and `DEMO_USER_PASSWORD` in your environment. Check the Socket.IO host and port in the same properties file.

```bash
./mvnw spring-boot:run
```

The RestAssured tests call `http://localhost:8080`. Run the app and MySQL first, then run `./mvnw test` in another terminal.

# IoT Gateway Backend

This Java and Spring Boot app receives device data over REST and Socket.IO. It stores data in MySQL and gives access to signed-in clients.

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

Run `./mvnw test` with the test database and a running MySQL server.

# IoT Gateway Backend

This project is a backend system for managing IoT devices and their data communication via a centralized server using Spring Boot and WebSockets (Netty SocketIO).  
It enables devices to send and receive data through a common gateway, using REST APIs or real-time socket communication.

---

## 🧩 What It Does

- Accepts data from IoT devices via REST endpoints or SocketIO.
- Persists incoming device data to a MySQL database.
- Exposes real-time data using WebSocket (Netty SocketIO).
- Provides secured user and API key management via JWT-based authentication.
- Offers Swagger documentation for all REST endpoints.
- Includes a full test suite using RestAssured and JUnit.

---

## 📦 Technologies

- **Java 17**
- **Spring Boot 3.1.4**
- **Netty-SocketIO**
- **Spring Security + JWT**
- **MySQL** (Production & Test)
- **Spring Data JPA**
- **Swagger/OpenAPI (springdoc)**
- **Rest-Assured / Hamcrest (Testing)**
- **Lombok, ModelMapper, Hypersistence-utils**

---

## 📁 Key Modules

- `/iotdata` → Device data handling
- `/userTest`, `/accountTest`, `/clientApiKeysTest` → Test classes for key modules
- `application.properties` → Main configuration
- `application-test.properties` → Separate test database config

---

## 🔐 Security

- JWT authentication (Token-based)
- `admin / 12345` default user (for local dev)
- API Key support for client/device-level auth
- All routes protected with Spring Security

---

## ⚡ Real-Time Communication

- Devices can connect over **SocketIO (port 8085)**.
- Data sent by one device can be received by others listening via sockets.
- Allows server-mediated, real-time message distribution between devices.

---

## 🧪 Testing

- Uses **RestAssured** for endpoint testing.
- Separate MySQL test DB (`application-test.properties`).
- `ddl-auto=create-drop` ensures fresh schema per test run.

---

## ▶️ How to Run

```bash
# 1. Make sure you have Java 17 and MySQL running
# 2. Configure application.properties if needed
# 3. Run using Maven
./mvnw spring-boot:run
```
old and good days, 2023 winter.
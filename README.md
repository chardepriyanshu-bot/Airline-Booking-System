# Airline Booking System ✈️

A microservices-based Airline Booking System developed using Java and Spring Boot. The project is designed to manage airline bookings through independent services for users, flights, seat inventory, payments, and related operations.

## 🚀 Features

- **Microservices Architecture** – Independent services for different business functionalities.
- **API Gateway** – Centralized entry point for routing API requests.
- **User Authentication** – JWT-based authentication and authorization.
- **Booking Service** – Handles flight booking operations.
- **Flight Management** – Manages flight, airline, airport, and schedule-related operations.
- **Seat Inventory Service** – Manages seat availability and inventory.
- **Payment Service** – Handles payment-related operations and booking-payment integration.
- **Global Exception Handling** – Consistent error handling across services.
- **Service Discovery** – Eureka Server for service registration and discovery.
- **Kafka Integration** – Supports event-driven communication.
- **Redis Caching** – Used for caching to improve performance.

## 🛠️ Technologies Used

- **Language:** Java 21
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Architecture:** Microservices
- **Security:** Spring Security, JWT
- **API Gateway:** Spring Cloud Gateway
- **Service Discovery:** Netflix Eureka
- **Messaging:** Apache Kafka
- **Caching:** Redis
- **Database:** Configured databases depending on the individual service
- **Inter-service Communication:** OpenFeign / REST APIs

## 🏗️ Microservices

| Service | Responsibility |
|---|---|
| API Gateway | Routes incoming API requests |
| User Service | User management |
| Airline/Aircraft Service | Airline and aircraft management |
| Airport-City Service | Airport and city-related operations |
| Flight Service | Flight management |
| Flight Schedule Service | Flight schedules |
| Seat Inventory Service | Seat availability and inventory |
| Booking Service | Booking management |
| Payment Service | Payment processing |
| Notification Service | Notification-related operations |
| Eureka Server | Service discovery |
| Common Library | Shared code and reusable components |

## ⚙️ Prerequisites

- Java JDK 21
- Maven
- Docker Desktop
- IDE such as Eclipse or IntelliJ IDEA
- Git

## ▶️ How to Run

1. Clone the repository.
2. Import the Maven projects into your IDE.
3. Ensure the required database configuration is set for each service.
4. Start the required infrastructure services, including Eureka, Kafka, and Redis.
5. Start the individual microservices with their required configurations.
6. Start the API Gateway.
7. Send API requests through the configured Gateway endpoints.

**Note:** Configure service ports, database credentials, Kafka, Redis, and Eureka settings according to each service's configuration before running the application.

## 🔐 Security

The project uses JWT-based authentication to secure protected API endpoints. Authentication and authorization rules are configured through the security components.

## 📌 Project Objective

The objective of this project is to demonstrate practical implementation of microservices architecture, REST APIs, service discovery, authentication, inter-service communication, event-driven messaging, caching, and centralized API routing.

## 👨‍💻 Author

**Priyanshu Charde**


# Student Grade Management API

[![CI](https://github.com/2403051050553/student-grade-management/actions/workflows/ci.yml/badge.svg)](https://github.com/2403051050553/student-grade-management/actions/workflows/ci.yml)
[![Java 17](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot 3.2](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A Spring Boot REST API for managing students and their grades. The repository currently contains the Java backend; it does not include a React frontend.

## Features

- Student create, read, update, and delete endpoints
- Grade create, update, delete, and lookup by student or course
- Login endpoint that issues a JWT
- Request validation and Spring Data JPA persistence
- OpenAPI/Swagger UI integration

## Technology

- Java 17
- Spring Boot 3.2
- Spring Web, Spring Data JPA, Spring Security, and Bean Validation
- MySQL
- Maven
- JWT (JJWT) and Springdoc OpenAPI

## Project layout

```text
backend/
├── pom.xml
└── src/
    └── main/
        ├── java/com/studentgrades/
        │   ├── controller/
        │   ├── dto/
        │   ├── model/
        │   ├── repository/
        │   ├── security/
        │   └── service/
        └── resources/
            └── application.properties
```

## Run locally

### Requirements

- JDK 17
- Maven
- MySQL

### 1. Create the database

Create a local database before starting the application:

```sql
CREATE DATABASE student_grades;
```

### 2. Configure database credentials and JWT secret

The application reads Spring Boot configuration from environment variables. Set credentials for a local MySQL user that can access `student_grades`; do not commit real passwords or signing keys.

PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/student_grades?serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME = "your_local_mysql_user"
$env:SPRING_DATASOURCE_PASSWORD = "your_local_mysql_password"
$env:JWT_SECRET = "replace-with-a-long-random-secret"
```

Bash:

```bash
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/student_grades?serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='your_local_mysql_user'
export SPRING_DATASOURCE_PASSWORD='your_local_mysql_password'
export JWT_SECRET='replace-with-a-long-random-secret'
```

### 3. Start the API

Run from the repository root:

```bash
mvn -f backend/pom.xml spring-boot:run
```

The server listens on port `8080` and uses `/api` as its context path.

## API overview

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/auth/login` | Authenticate and request a JWT |
| `GET` | `/api/students` | List students |
| `GET` | `/api/students/{id}` | Get a student |
| `POST` | `/api/students` | Create a student |
| `PUT` | `/api/students/{id}` | Update a student |
| `DELETE` | `/api/students/{id}` | Delete a student |
| `POST` | `/api/grades` | Add a grade |
| `GET` | `/api/grades/student/{studentId}` | List grades for a student |
| `GET` | `/api/grades/course/{courseId}` | List grades for a course |
| `PUT` | `/api/grades/{id}` | Update a grade |
| `DELETE` | `/api/grades/{id}` | Delete a grade |

Interactive API documentation is configured at `/api/swagger-ui.html`.

## Build and test

From the repository root:

```bash
mvn -B -f backend/pom.xml verify
```

GitHub Actions runs this verification on pushes to `main` and on pull requests targeting `main`.

## Contributing

Bug reports and focused improvements are welcome. Open an issue to discuss a larger change, or submit a pull request with a clear description and verification steps. Run the Maven verification command before opening a pull request.

## License

This project is available under the [MIT License](LICENSE).

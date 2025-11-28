Spring Boot + Kotlin REST API (Users → Posts → Comments)

A clean and modular REST API built using Spring Boot 3 and Kotlin, featuring JWT authentication, relational data modeling, Swagger documentation, and optional Dockerized MySQL environment.

Features

Kotlin + Spring Boot 3

Spring Security with JWT (stateless authentication)

Role-based authorization (USER / ADMIN)

JPA / Hibernate ORM

H2 by default, MySQL-ready

DTO ↔ Entity mapping using Kotlin extensions

One-to-many data model (User → Post → Comment)

Pagination and sorting

Global exception handling

CORS enabled for development

Swagger / OpenAPI documentation

Dockerfile + docker-compose support

Run the Application (Local)
./gradlew clean bootRun


After startup:

App: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

H2 Console: http://localhost:8080/h2-console

Authentication (JWT)

Public endpoints:

/auth/register
/auth/login


All other endpoints require a valid JWT token.

Register
curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{"name":"Ali","email":"ali@example.com","password":"secret123"}'

Login
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"ali@example.com","password":"secret123"}'


Example response:

{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}

Use token in authorized requests

Swagger UI → Authorize:

Bearer <your-token>


Or via curl:

curl http://localhost:8080/users \
-H "Authorization: Bearer <your-token>"

Example API Requests
Users
# Create user
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"name":"Ali","email":"ali@example.com"}'

# List users
curl "http://localhost:8080/users?page=0&size=10&sort=id,desc" \
-H "Authorization: Bearer <token>"

# Get user
curl http://localhost:8080/users/1 \
-H "Authorization: Bearer <token>"

Posts
# Create post
curl -X POST http://localhost:8080/users/1/posts \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"title":"My First Post","content":"Hello!"}'

# List user posts
curl "http://localhost:8080/users/1/posts?page=0&size=5" \
-H "Authorization: Bearer <token>"

Comments
# Add comment
curl -X POST http://localhost:8080/posts/1/comments \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"comment":"Nice post!"}'

# List comments
curl "http://localhost:8080/posts/1/comments?page=0&size=10" \
-H "Authorization: Bearer <token>"

Configuration
JWT Secret

In application.yml:

app.jwt.secret=your-secret-key

Database Profiles

Default: H2 (in-memory)

To run using MySQL profile:

./gradlew bootRun --args="--spring.profiles.active=mysql"

Running with Docker (MySQL + App)

The project includes:

Dockerfile

docker-compose.yml

Build & run
docker compose up -d --build


Services:

App → http://localhost:8080

MySQL → accessible at host port 3307

MySQL configuration inside app:

spring.datasource.url=jdbc:mysql://db:3306/demo
spring.datasource.username=root
spring.datasource.password=root

Tech Stack
Layer	Technology
Language	Kotlin
Framework	Spring Boot 3
Security	Spring Security + JWT
ORM	Hibernate / JPA
Database	H2 / MySQL
Docs	Swagger / springdoc-openapi
Build Tool	Gradle (Kotlin DSL)
Deployment	Docker & docker-compose
Author

Created by pelin0866
Built using Kotlin and Spring Boot
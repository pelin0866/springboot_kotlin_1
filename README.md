# springboot_kotlin_1
Kotlin Spring Boot REST API_1
# Spring Boot + Kotlin Demo (Users ▸ Posts ▸ Comments)

A clean REST API built with Spring Boot and Kotlin.

Main features:
- Kotlin + Spring Boot
- Spring Security with JWT (stateless authentication)
- JPA / Hibernate (H2 by default, ready for MySQL)
- DTO ↔ Entity mapping via Kotlin extensions
- One-to-Many / Many-to-One relationships (User → Post → Comment)
- Pagination and sorting
- Swagger / OpenAPI documentation
- CORS support for development

---

## Run the Application

```bash
./gradlew clean bootRun
After the application starts:

App: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

H2 Console (if enabled): http://localhost:8080/h2-console

Authentication (JWT)
The API uses stateless JWT authentication.
All /auth/** endpoints are public; other endpoints require a valid token.

Register
bash
Copy code
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Ali","email":"ali@example.com","password":"secret123"}'
Login
bash
Copy code
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"ali@example.com","password":"secret123"}'
Response example:

json
Copy code
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..." }
Authorized requests
In Swagger UI, click Authorize and enter:

php-template
Copy code
Bearer <your-token>
Or use curl:

bash
Copy code
curl http://localhost:8080/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIs..."
Example Requests
Users
bash
Copy code
# Create a user
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ali","email":"ali@example.com"}'

# List users with pagination
curl "http://localhost:8080/users?page=0&size=10&sort=id,desc"

# Get user by ID
curl http://localhost:8080/users/1
Posts
bash
Copy code
# Create a post
curl -X POST http://localhost:8080/users/1/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"My First Post","content":"Hello, world!"}'

# List user's posts
curl "http://localhost:8080/users/1/posts?page=0&size=5"
Comments
bash
Copy code
# Add a comment
curl -X POST http://localhost:8080/posts/1/comments \
  -H "Content-Type: application/json" \
  -d '{"comment":"Great post!"}'

# List comments
curl "http://localhost:8080/posts/1/comments?page=0&size=10"
Configuration
JWT Secret: defined in application.yml under app.jwt.secret

Database:
Default → H2 in-memory
For MySQL:

bash
Copy code
./gradlew bootRun --args="--spring.profiles.active=mysql"
CORS: all origins allowed for development; restrict in production.

Session: stateless (no cookies or server sessions)

Tech Stack
Layer	Technology
Language	Kotlin
Framework	Spring Boot 3 (Web, Security, Data JPA)
Authentication	JWT (Bearer token)
ORM	Hibernate / JPA
Database	H2 / MySQL
Documentation	Swagger / springdoc-openapi
Build tool	Gradle (Kotlin DSL)

Author: pelin0866
Built with ❤️ using Kotlin & Spring Boot
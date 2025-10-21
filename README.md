# springboot_kotlin_1
Kotlin Spring Boot REST API_1
# Spring Boot + Kotlin Demo (Users ▸ Posts ▸ Comments)

Basit bir REST API:
- Kotlin + Spring Boot
- JPA/Hibernate (H2→MySQL’e hazır)
- DTO ↔ Entity mapper (extension)
- One-to-Many / Many-to-One (User→Post, Post→Comment)
- Pagination & Sorting
- Swagger/OpenAPI

## Çalıştırma
```bash
./gradlew clean bootRun
# Uygulama: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui/index.html

## Ornek Istekler
###User 
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"name":"Ali","email":"ali@example.com"}'
curl "http://localhost:8080/users?page=0&size=10&sort=id,desc"
curl http://localhost:8080/users/1

###Post
curl -X POST http://localhost:8080/users/1/posts -H "Content-Type: application/json" -d '{"title":"İlk yazım","content":"Merhaba!"}'
curl "http://localhost:8080/users/1/posts?page=0&size=5"

###Comment
curl -X POST http://localhost:8080/posts/1/comments -H "Content-Type: application/json" -d '{"comment":"Harika yazı!"}'
curl "http://localhost:8080/posts/1/comments?page=0&size=10"


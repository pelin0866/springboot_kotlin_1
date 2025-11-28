# Build stage
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /home/gradle/src
COPY . .
RUN gradle clean bootJar --no-daemon

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
# Multi-stage build. Usa un a imagen docker diferente para compilar
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:17-alpine
COPY --from=builder /app/target/equipos.jar .
CMD ["java", "-jar", "/equipos.jar"]

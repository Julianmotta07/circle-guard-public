FROM eclipse-temurin:21-jdk-alpine AS builder
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY services/circleguard-form-service/build.gradle.kts services/circleguard-form-service/build.gradle.kts
COPY services/circleguard-form-service/src services/circleguard-form-service/src
RUN sed -i 's/\r//' gradlew && chmod +x gradlew && ./gradlew :services:circleguard-form-service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/services/circleguard-form-service/build/libs/*.jar app.jar
EXPOSE 8086
CMD ["java", "-jar", "app.jar"]
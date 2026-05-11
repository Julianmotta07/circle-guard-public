FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY services/circleguard-identity-service/build.gradle.kts services/circleguard-identity-service/build.gradle.kts
COPY services/circleguard-identity-service/src services/circleguard-identity-service/src
RUN sed -i 's/\r//' gradlew && chmod +x gradlew && ./gradlew :services:circleguard-identity-service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/services/circleguard-identity-service/build/libs/*.jar app.jar
EXPOSE 8083
CMD ["java", "-jar", "app.jar"]
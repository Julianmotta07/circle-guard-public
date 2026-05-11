FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY services/circleguard-promotion-service/build.gradle.kts services/circleguard-promotion-service/build.gradle.kts
COPY services/circleguard-promotion-service/src services/circleguard-promotion-service/src
RUN sed -i 's/\r//' gradlew && chmod +x gradlew && ./gradlew :services:circleguard-promotion-service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/services/circleguard-promotion-service/build/libs/*.jar app.jar
EXPOSE 8088
CMD ["java", "-jar", "app.jar"]
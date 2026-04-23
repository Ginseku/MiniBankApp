FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine

WORKDIR /miniBankApi
COPY --from=builder build/build/libs/*.jar miniBank.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "miniBank.jar"]
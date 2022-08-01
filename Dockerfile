FROM maven:3.8-openjdk-11 AS builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk
COPY --from=builder ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
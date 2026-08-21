FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src

RUN chmod +x mvnw && ./mvnw package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/job-portal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
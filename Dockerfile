FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8083

CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT:-8083} --server.address=0.0.0.0"]
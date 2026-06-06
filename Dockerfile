# --- Build stage: kompajlira aplikaciju iz izvornog koda ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Prvo samo pom.xml -> keširaj zavisnosti u zaseban sloj (brži rebuild)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Pa izvorni kod i build (testovi se preskaču; oni se vrte u 'test' job-u)
COPY src ./src
RUN mvn -q clean package -DskipTests

# --- Run stage: lagan JRE image samo sa gotovim JAR-om ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Kopira tačno izgrađen JAR (bez hardkodiranog imena)
COPY --from=build /app/target/*.jar app.jar

# Aktivacija prod profila (Render env var SPRING_PROFILES_ACTIVE ovo može da pregazi)
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

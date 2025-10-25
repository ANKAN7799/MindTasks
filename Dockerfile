# Stage 1: build with Maven (inside container) - no local Maven needed
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy pom first to take advantage of dependency cache
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# Stage 2: runtime image with JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar from build stage
ARG JAR_FILE=/workspace/target/*.jar
COPY --from=build ${JAR_FILE} app.jar

# Expose port for Render
EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

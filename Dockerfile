FROM gradle:8-jdk11-alpine AS gradle
COPY . .
RUN gradle clean build

FROM eclipse-temurin:17-jre-alpine
COPY --from=gradle ./build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profile.active=dev", "app.jar"]
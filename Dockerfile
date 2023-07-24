FROM gradle:8-jdk17-alpine AS gradle
COPY . .
RUN gradle clean build

FROM eclipse-temurin:17-jre-alpine
COPY --from=gradle /home/gradle/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]
FROM eclipse-temurin:11-jre-alpine
WORKDIR app
COPY ./build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profile.active=dev", "app.jar"]
FROM gradle:8-jdk11-alpine AS gradle
COPY . .
EXPOSE 8080
RUN gradle bootDevRun
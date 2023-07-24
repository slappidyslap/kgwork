FROM gradle:8-jdk11-alpine
COPY . .
EXPOSE 8080
RUN gradle bootDevRun
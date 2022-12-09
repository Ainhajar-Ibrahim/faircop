FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/faircorp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

#docker run -p 8080:8080 faircorp/backend
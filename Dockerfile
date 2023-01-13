# Build a JAR File
FROM gradle:7.1.0-jdk11 AS buildStage
WORKDIR /home/app
COPY . /home/app/
RUN ./gradlew build

# Create an Image
FROM openjdk:11
EXPOSE 8080
COPY --from=buildStage /home/app/build/libs/user-api-1.0.0.jar user-api.jar
ENTRYPOINT ["sh", "-c", "java -jar /user-api.jar"]

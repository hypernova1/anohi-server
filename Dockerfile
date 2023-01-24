FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} /
ENTRYPOINT ["java", "-jar", "app.jar"]
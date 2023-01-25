FROM adoptopenjdk/openjdk11 as builder

EXPOSE 8080

ADD ./build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
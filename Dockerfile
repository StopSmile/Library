FROM openjdk:17-alpine
ADD book-service-rest/target/book-service-rest-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]

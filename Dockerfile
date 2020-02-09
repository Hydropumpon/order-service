FROM openjdk:8-jre-alpine
COPY ./build/libs/order-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]

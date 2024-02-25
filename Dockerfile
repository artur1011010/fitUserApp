FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} fitUserApp0.0.3.jar
EXPOSE 8081

ENTRYPOINT ["java","-jar","/fitUserApp0.0.3.jar"]
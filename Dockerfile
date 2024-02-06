FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} fitUserApp-0.1.0.jar
EXPOSE 8081

ENTRYPOINT ["java","-jar","/fitUserApp-0.1.0.jar"]
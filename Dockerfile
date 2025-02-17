FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/spring-ai-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring-ai-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/spring-ai-0.0.1-SNAPSHOT.jar"]
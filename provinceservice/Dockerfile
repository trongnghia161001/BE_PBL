FROM openjdk:17

ARG JAR_FILE=target/provinceservice-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} provinceservice.jar

ENTRYPOINT ["java", "-jar", "/provinceservice.jar"]

EXPOSE 9025
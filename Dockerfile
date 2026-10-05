# Linux with Java 17 runtime. Version must match pom.xml and the workflow.
FROM eclipse-temurin:17-jre
COPY target/app.jar /tmp/app.jar
WORKDIR /tmp
# db:3306 = the database service name and port inside the compose network; 10000 = retry delay in ms
ENTRYPOINT ["java", "-jar", "app.jar", "db:3306", "10000"]

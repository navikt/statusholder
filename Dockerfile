FROM maven:3.6.3-openjdk-17 AS maven

COPY pom.xml pom.xml

COPY . .

RUN mvn clean install

RUN mvn dependency:go-offline -B

RUN mvn package

FROM eclipse-temurin:17-jre

#RUN dir #Added

WORKDIR /adevguide

EXPOSE 8080


#RUN dir #Added


COPY --from=maven target/statusholder-0.0.1-SNAPSHOT.jar ./statusholder.jar

CMD ["java", "-jar", "./statusholder.jar"]
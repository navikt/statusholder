FROM maven:3.6.3-openjdk-17 as maven

COPY pom.xml pom.xml

COPY . .

RUN mvn clean install

RUN mvn dependency:go-offline -B

RUN mvn package

FROM openjdk:17

#RUN dir #Added

WORKDIR /adevguide

EXPOSE 8080


#RUN dir #Added


COPY --from=maven statusholder/target/statusholder-0.0.1-SNAPSHOT.jar ./statusholder.jar

CMD ["java", "-jar", "./statusholder.jar"]
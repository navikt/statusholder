FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jdk:openjdk-21-dev AS maven

# /home/build is the image's default workdir and is owned by the nonroot user;
# --chown makes the copied sources writable so Maven can create target/.
WORKDIR /home/build

COPY --chown=java:java . .

RUN ./mvnw clean package -B -DskipTests

FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21

WORKDIR /adevguide

EXPOSE 8080

COPY --from=maven /home/build/target/statusholder-0.0.1-SNAPSHOT.jar ./statusholder.jar

# Chainguard jre sets ENTRYPOINT to /usr/bin/java, so override it here rather
# than using CMD (which would be passed as args to /usr/bin/java).
ENTRYPOINT ["java", "-jar", "./statusholder.jar"]
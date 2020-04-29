FROM adoptopenjdk/openjdk11
LABEL "Description" = "Storage Service"
COPY target/*.jar storageservice.jar
ENTRYPOINT ["java", "-jar", "/storageservice.jar"]

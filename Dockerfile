# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# Copy FAT jar into image
COPY /build/libs/hello-consumer-microservice-1.0-SNAPSHOT-all.jar /version-RC1.jar
# run application with the command line
CMD ["/usr/bin/java", "-jar", "-Dhsqldb.reconfig_logging=false", "/version-RC1.jar"]

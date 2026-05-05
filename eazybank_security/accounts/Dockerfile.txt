#Start with base image containing Java runtime
FROM eclipse-temurin:17-jdk-alpine

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER NilNS07.com
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="nins07.com"

#Add the jar application's jar to the image.
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#excute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]
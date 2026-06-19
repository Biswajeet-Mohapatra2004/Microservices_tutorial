#Start with a base image containing java runtime
FROM openjdk:21-ea-10-jdk

MAINTAINER biswajeetmohapatra.vercel.app

#copy the jar file into the docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#execute the command to run the jar file
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]


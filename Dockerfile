FROM openjdk:21-jdk-slim

WORKDIR /app
# Copy bff service .jar
COPY build/libs/bff-service-1.0.0.jar  bff-service.jar

# Copy wait-for-it.sh script
COPY ./wait-for-it.sh  app/wait-for-it.sh

# Make wait-for-it executable
RUN chmod  +x  app/wait-for-it.sh

# Command to execute the JAR, but it will be overridden by entrypoint in docker-compose.yml
#ENTRYPOINT ["java", "-jar", "bff-service.jar"]  #Normal config
#for Debug
EXPOSE 8086 5005
#for Debug
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "bff-service.jar"]

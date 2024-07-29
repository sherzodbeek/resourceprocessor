FROM amazoncorretto:21-alpine-jdk

COPY build/libs/resourceprocessor-0.0.1-SNAPSHOT.jar /home/resourceprocessor.jar
CMD ["java", "-jar", "/home/resourceprocessor.jar"]

# docker build -t resource-processor .
#docker run --name resource-processor-container -d resource-processor
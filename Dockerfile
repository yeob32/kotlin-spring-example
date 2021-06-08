FROM openjdk:11.0.7-jre-slim
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
LABEL maintainer="yeob32@github.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
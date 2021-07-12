FROM openjdk:11.0.7-jre-slim

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

LABEL maintainer="yeob32@github.com"

# RUN, CMD, ENTRYPOINT 의 명령이 실행될 티렉터리 설정
# WORKDIR /root

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/\*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar -DSpring.profiles.active=local", "/app.jar"]
#CMD java -jar demo-0.0.1-SNAPSHOT.jar

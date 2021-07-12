# kotlin-spring-example

## QueryDSL
### Build

```bash
$ ./gradlew clean compileKotlin
```

## Build

### build jar
- build -x test
    - 테스트 스킵
```shell
$ ./gradlew clean && ./gradlew build -x test && java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Docker build
```shell
$ docker build --build-arg JAR_FILE=build/libs/\*.jar -t app .
$ docker build -t app .
```
###
```shell
$ docker network create referring

$ docker container inspect mysql-master
$ docker container inspect mysql-master | grep -A 20 'referring'
$ docker network inspect referring
```


### Docker run
```shell
$ docker run --name application -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 app
$ docker run -d --name application -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 --network referring --link mysql-master yeob32/kotlin-spring-example
$ docker run --name application -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 --network referring --link mysql-master:mysql-master yeob32/kotlin-spring-example
```

### Error
```
Execution failed for task ':bootJarMainClassName'.
> Unable to find a single main class from the following candidates [com.example.demo.DemoApplicationKt, com.example.demo.global.error.ErrorResponseKt, com.example.demo.global.error.exception.ApiExceptionKt]
```

```groovy
// 메인 클래스 명시해주도록 하자
springBoot {
    mainClassName = "com.example.demo.DemoApplicationKt"
}
```

### application.yaml 마이그레이션 이슈
```shell
18:59:06.534 [main] ERROR org.springframework.boot.SpringApplication - Application run failed
org.springframework.boot.context.config.InvalidConfigDataPropertyException: Property 'spring.profiles.active' imported from location 'class path resource [application-h2.yml]' is invalid in a profile specific resource [origin: class path resource [application-h2.yml] - 3:13]
	at org.springframework.boot.context.config.InvalidConfigDataPropertyException.lambda$throwOrWarn$1(InvalidConfigDataPropertyException.java:124)
	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
```

````yaml
spring:
  profiles:
    active: h2

## 2.4 이후
spring:
  config:
    activate:
      on-profile: h2
````


### Reference
- https://spring.io/guides/gs/spring-boot-docker/

#### Exception Handling
- https://www.baeldung.com/exception-handling-for-rest-with-spring
- https://medium.com/sprang/validating-inner-layers-with-spring-7216b7f221f
- https://medium.com/sprang/validation-and-exception-handling-with-spring-ba44b3ee0723
- https://pjh3749.tistory.com/273
- https://onecellboy.tistory.com/347
- https://developers.worksmobile.com/jp/document/1003012?lang=ko
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
$ docker build --build-arg JAR_FILE=build/libs/\*.jar -t yeob32/kotlin-spring-example .
$ docker build -t yeob32/kotlin-spring-example .
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


### Reference
- https://spring.io/guides/gs/spring-boot-docker/

#### Exception Handling
- https://www.baeldung.com/exception-handling-for-rest-with-spring
- https://medium.com/sprang/validating-inner-layers-with-spring-7216b7f221f
- https://medium.com/sprang/validation-and-exception-handling-with-spring-ba44b3ee0723
- https://pjh3749.tistory.com/273
- https://onecellboy.tistory.com/347
- https://developers.worksmobile.com/jp/document/1003012?lang=ko
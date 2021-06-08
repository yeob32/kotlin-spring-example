import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31" // kotlin("plugin.noarg") 포함

    // Lazy 전략 사용 시 실제 객체 호출 전까지 프록시 객체 참조
    // 프록시 객체는 Entity 클래스 확장
    // JPA final class -> 확장 불가능 -> @ManyToOne Lazy 동작 안함
    // kotlin class -> 기본 final, open 키워드 제공
    // allopen plugin -> open class 로 바꿔줌
    // 그냥 플러그인 안쓰고 클래스별로 open 붙인다고 되는게 아니다.
    // 프로퍼티와 함수에도 open 키워드가 필요하다.
    // https://spring.io/guides/tutorials/spring-boot-kotlin/ 해당 문서에서는 allopen 플로그인 사용 권장하고 있음
    kotlin("plugin.allopen") version "1.4.32"
    kotlin("kapt") version "1.4.10" // annotation processing을 위한 kapt
}

// Only @Entity 클래스
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

// Hibernate 에서는 인자 없는 디폴트 생성자 필요
// @Entity 클래스 no arg 플러그인 적용
noArg {
    annotation("javax.persistence.Entity")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring 이랑 dependency 안맞으면 에러남 java.lang.UnsupportedOperationException
    api("com.querydsl:querydsl-jpa")
    // kapt로 dependency를 지정해 준다.
    // kotlin 코드가 아니라면 kapt 대신 annotationProcessor를 사용한다.
    kapt("com.querydsl:querydsl-apt::jpa")

    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")

    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClassName = "com.example.demo.DemoApplicationKt"
}

//sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class){
//    kotlin.srcDir("$buildDir/generated/source/kapt/main")
//}

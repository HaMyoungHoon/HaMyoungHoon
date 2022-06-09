import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    war
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "hamyounghoon"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    //region spring boot: web, security, mybatis
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
    //endregion
    //region kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    //endregion
    //region swagger : rest api doc
    implementation("org.springdoc:springdoc-openapi-ui:1.6.7")
    //endregion

    // yaml parse
    implementation("net.rakugakibox.util:yaml-resource-bundle:1.1")
    // json automation
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    // jwt login token
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    // json mapper
    implementation("com.google.code.gson:gson:2.9.0")
    // excel read/write
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    implementation("net.sf.json-lib:json-lib:2.4:jdk15")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:9.4.1.jre11")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:2.6.7")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

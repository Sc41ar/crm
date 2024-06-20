 plugins {
    id("java")
    id("org.springframework.boot") version "3.3.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.0")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security:3.3.0")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-gradle-plugin
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.0")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.3.0")
    // https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    // https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.3")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}

extra.apply {
    set("db_url", "jdbc:postgresql://81.31.244.60:5433/root")
    set("db_user", "user")
    set("db_pass", "gnnaIaoL0P")
}

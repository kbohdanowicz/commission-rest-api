import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springVersion: String by project
val junitVersion: String by project
val mockkVersion: String by project
val commonsCsvVersion: String by project
val serializationVersion: String by project
val dotenvVersion: String by project

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.serialization") version "1.7.22"
}

group = "kbohdanowicz"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    testImplementation("org.springframework.security:spring-security-test:$springVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")

    // CSV
    implementation("org.apache.commons:commons-csv:$commonsCsvVersion")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // Dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")
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

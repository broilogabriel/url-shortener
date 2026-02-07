import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.10"
    kotlin("plugin.serialization") version "2.3.10"
    id("io.ktor.plugin") version "3.4.0"
}

group = "io.github.broilogabriel"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    
    // Database - Ktorm
    implementation("org.ktorm:ktorm-core:4.1.1")
    implementation("com.h2database:h2:2.4.240")
    implementation("com.zaxxer:HikariCP:7.0.2")
    
    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.13")
    
    // Testing
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.13.8")
}

application {
    mainClass.set("io.github.broilogabriel.ApplicationKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

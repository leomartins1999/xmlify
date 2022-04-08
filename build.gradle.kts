group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    val testLoggerVersion = "3.2.0"
    val ktlintVersion = "10.2.1"
    val kotlinVersion = "1.6.20"

    id("com.adarshr.test-logger") version testLoggerVersion
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion

    kotlin("jvm") version kotlinVersion
}

dependencies {
    val junitVersion = "5.8.2"

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

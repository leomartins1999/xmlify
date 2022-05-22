plugins {
    val ktlintVersion = "10.2.1"
    val testLoggerVersion = "3.2.0"
    val kotlinVersion = "1.6.21"

    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    id("com.adarshr.test-logger") version testLoggerVersion

    kotlin("jvm") version kotlinVersion
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("com.adarshr.test-logger")
        plugin("org.jetbrains.kotlin.jvm")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        val junitVersion = "5.8.2"

        testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    }
}

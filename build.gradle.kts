import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.leomartins1999"

plugins {
    val kotlinVersion = "1.6.20"

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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    val testLoggerVersion = "3.2.0"
    val ktlintVersion = "10.2.1"
    val kotlinVersion = "1.6.20"

    id("com.adarshr.test-logger") version testLoggerVersion
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    id("org.jetbrains.dokka") version kotlinVersion

    kotlin("jvm") version kotlinVersion
}

dependencies {
    val junitVersion = "5.8.2"

    implementation(kotlin("reflect"))

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

repositories {
    mavenCentral()
}

sourceSets.create("samples") {
    java.srcDir("src/samples/kotlin")
    compileClasspath += sourceSets.getByName("main").runtimeClasspath
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("xmlify")
            documentedVisibilities.set(setOf(org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC))
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/leomartins1999/xmlify/tree/main/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

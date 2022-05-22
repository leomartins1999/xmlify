import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    val kotlinVersion = "1.6.21"

    id("org.jetbrains.dokka") version kotlinVersion

    `java-library`
}

dependencies {
    implementation(kotlin("reflect"))
}

repositories {
    mavenCentral()
}

sourceSets.create("samples") {
    java.srcDir("src/samples/kotlin")
    compileClasspath += sourceSets.getByName("main").runtimeClasspath
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

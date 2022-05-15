plugins {
    val composeVersion = "1.1.1"
    val kotlinVersion = "1.6.10"

    id("org.jetbrains.compose") version composeVersion

    kotlin("jvm") version kotlinVersion
}

dependencies {
    implementation(project(":core"))

    implementation(compose.desktop.currentOs)
}

repositories {
    mavenCentral()
}

compose.desktop {
    application {
        mainClass = "com.github.leomartins1999.xmlify.AppKt"
    }
}

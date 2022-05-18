group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    val kotlinVersion = "1.6.21"

    kotlin("jvm") version kotlinVersion
    application
}

dependencies {
    implementation(project(":core"))
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.github.leomartins1999.xmlify.AppKt")
}

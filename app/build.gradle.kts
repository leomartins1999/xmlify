group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    application
}

dependencies {
    implementation(project(":core"))

    testImplementation(kotlin("reflect"))
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.github.leomartins1999.xmlify.AppKt")
}

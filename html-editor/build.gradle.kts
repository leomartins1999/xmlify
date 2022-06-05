group = "com.github.leomartins1999"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    application
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.github.leomartins1999.xmlify.AppKt")
}

plugins {
    kotlin("jvm") version "1.9.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.json:json:20230227")
}

application {
    mainClass.set("MainKt")
}

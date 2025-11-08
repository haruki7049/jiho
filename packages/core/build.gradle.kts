plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.dirs:directories:26")
    implementation("com.google.code.gson:gson:2.13.2")
}

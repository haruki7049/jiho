plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.dirs:directories:26")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.0")
}

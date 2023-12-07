plugins {
    kotlin("jvm") version "1.6.10"
    id("maven-publish")
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}

group = "ru.rudikov"
version = "0.1.0"

gradlePlugin {
    plugins {
        create("schema-registry-buddy") {
            id = "ru.rudikov.schema-registry-buddy"
            implementationClass = "ru.rudikov.schemaregistrybuddy.SchemaRegistryBuddy"
        }
    }
}

dependencies {
    implementation("org.apache.avro:avro:1.11.3")
    implementation("org.json:json:20231013")

}
plugins {
    kotlin("jvm") version "1.6.10"
    id("maven-publish")
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}


gradlePlugin {
    plugins {
        create("schema-registry-buddy") {
            id = "ru.rudikov.schema-registry-buddy"
            implementationClass = "ru.rudikov.schemaregistrybuddy.SchemaRegistryBuddy"
        }
    }
}

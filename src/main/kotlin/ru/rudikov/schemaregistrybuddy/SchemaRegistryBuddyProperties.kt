package ru.rudikov.schemaregistrybuddy

class SchemaRegistryBuddyProperties(
    var schemaRegistryUrl: String = DEFAULT_SCHEMA_REGISTRY_URL,
    var searchPaths: Set<String> = DEFAULT_DIRECTORIES,
    var excludedPaths: Set<String> = emptySet(),
    var subjectToSchema: MutableMap<String, String> = mutableMapOf(),
) {
    companion object {
        private const val DEFAULT_SCHEMA_REGISTRY_URL = "http://localhost:10081"
        private val DEFAULT_DIRECTORIES = setOf("src/main/resources/avro/")
    }
}


package ru.rudikov.schemaregistrybuddy

open class SchemaRegistryBuddyProperties(
    var schemaRegistryUrl: String = DEFAULT_SCHEMA_REGISTRY_URL,
    var searchPaths: MutableSet<String> = mutableSetOf(),
    var subjectToSchema: MutableMap<String, String> = mutableMapOf(),
)

const val DEFAULT_SCHEMA_REGISTRY_URL = "http://localhost:10081"
const val CONTENT_TYPE_HEADER_VALUE = "application/vnd.schemaregistry.v1+json"
const val CONTENT_TYPE_HEADER_NAME = "Content-Type"


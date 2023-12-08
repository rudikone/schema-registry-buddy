package ru.rudikov.schemaregistrybuddy

import org.apache.avro.Schema
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import kotlin.io.path.isRegularFile

open class RegisterSchemas @Inject constructor(
    private val properties: SchemaRegistryBuddyProperties,
) : DefaultTask() {

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    @TaskAction
    fun register() {
        validateProperties()
        process()
    }

    private fun validateProperties() {
        if (properties.subjectToSchema.isEmpty()) {
            error("No schema has been announced!")
        }

        if (properties.searchPaths.isEmpty()) {
            properties.searchPaths.add("${project.buildDir}")
        }
    }

    private fun process() {
        properties.subjectToSchema.forEach { (subject, schemaName) ->
            properties.searchPaths.forEach { path ->
                runCatching {
                    val file = findAvroFileBySchemaName(schema = schemaName, path = path)
                    val schema = Schema.Parser().parse(file).toString()

                    val jsonValueOfSchema = JSONObject().put("schema", schema)

                    sendSchemaToRegistry(subject = subject, jsonObject = jsonValueOfSchema)
                }.onSuccess {
                    logger.lifecycle(it.body())
                }.onFailure {
                    logger.warn("Failed register $schemaName for $subject!", it)
                }
            }
        }
    }

    private fun findAvroFileBySchemaName(schema: String, path: String): File = Files.walk(Paths.get(path))
        .filter { it.isRegularFile() && it.fileName.toString() == "$schema.avsc" }
        .findFirst()
        .orElseThrow()
        .toFile()

    private fun sendSchemaToRegistry(subject: String, jsonObject: JSONObject): HttpResponse<String> {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI("${properties.schemaRegistryUrl}/subjects/$subject-value/versions"))
            .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
            .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
            .build()

        return httpClient.send(request, BodyHandlers.ofString())
    }
}

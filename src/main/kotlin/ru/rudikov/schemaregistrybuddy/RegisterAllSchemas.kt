package ru.rudikov.schemaregistrybuddy

import org.apache.avro.Schema
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.isRegularFile

class RegisterAllSchemas : DefaultTask() {

    private val properties = project.extensions.findByType(SchemaRegistryBuddyProperties::class.java)!!

    init {
        logger.info(properties.toString())
    }

    @TaskAction
    fun register() {
        properties.subjectToSchema.forEach { (subject, schema) ->
            properties.searchPaths.forEach { path ->

                val file = Files.walk(Paths.get(path))
                    .filter { it.isRegularFile() && it.fileName.toString() == "$schema.avsc" }
                    .findFirst()
                    .orElseThrow()
                    .toFile()

                println(Schema.Parser().parse(file).toString())
                println(subject)
            }
        }
    }
}
package ru.rudikov.schemaregistrybuddy

import org.gradle.api.Plugin
import org.gradle.api.Project

class SchemaRegistryBuddy : Plugin<Project> {

    private var registerAllSchemas: RegisterAllSchemas? = null

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, SchemaRegistryBuddyProperties::class.java)
        registerAllSchemas = project.tasks.create(REGISTER_ALL_SCHEMAS_TASK_NAME, RegisterAllSchemas::class.java)
    }

    companion object {
        private const val EXTENSION_NAME = "schemaRegistryBuddy"
        private const val REGISTER_ALL_SCHEMAS_TASK_NAME = "registerAllSchemas"
    }
}
package ru.rudikov.schemaregistrybuddy

import org.gradle.api.Plugin
import org.gradle.api.Project

class SchemaRegistryBuddy : Plugin<Project> {

    override fun apply(project: Project) {
        val properties = project.extensions.create(EXTENSION_NAME, SchemaRegistryBuddyProperties::class.java)

        project.tasks.create(
            REGISTER_SCHEMAS_TASK_NAME,
            RegisterSchemas::class.java,
            properties,
        )
    }

    companion object {
        private const val EXTENSION_NAME = "schemaRegistryBuddyProps"
        private const val REGISTER_SCHEMAS_TASK_NAME = "registerSchemas"
    }
}

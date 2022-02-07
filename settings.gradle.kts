// Set the name of the root project
rootProject.name = extra["base.id"] as String

// Set plugin versions
pluginManagement {
    plugins {
        id ("org.cadixdev.licenser") version (extra["licenser.version"] as String)
    }

    repositories {
        gradlePluginPortal()
    }
}

internal val projectNames: Map<String, List<String>> = mapOf(
    (extra["annotation.id"] as String) to listOf(
        extra["annotation.core.id"] as String,
        extra["annotation.processor.id"] as String,
        extra["annotation.all.id"] as String
    ),
    (extra["structure.id"] as String) to listOf(
        extra["structure.either.id"] as String,
        extra["structure.all.id"] as String
    )
)

// Add Project Builds
projectNames.forEach { (key, values) ->
    values.forEach {
        include("${key}:${key}-${it}")
    }
}
include(extra["all.id"] as String)

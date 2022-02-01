// Add plugins
plugins {
    `java-library`
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["structure.id"]}-${extra["structure.all.id"]}")
version = extra["structure.all.version"] as String

// Add dependencies
evaluationDependsOn(":structure:either")
dependencies {
    api(project(":structure:either"))
}

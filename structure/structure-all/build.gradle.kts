// Add plugins
plugins {
    `java-library`
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["structure.id"]}-${extra["structure.all.id"]}")
version = extra["structure.all.version"] as String

// Add dependencies
dependencies {
    api(project(":structure:structure-either"))
}

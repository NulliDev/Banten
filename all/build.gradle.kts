// Add plugins
plugins {
    `java-library`
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["all.id"]}")
version = extra["all.version"] as String

// Add dependencies
dependencies {
    api(project(":annotation:annotation-all"))
    api(project(":structure:structure-all"))
}

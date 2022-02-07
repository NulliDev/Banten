// Add plugins
plugins {
    `java-library`
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["annotation.id"]}-${extra["annotation.all.id"]}")
version = extra["annotation.all.version"] as String

// Add dependencies
dependencies {
    api(project(":annotation:annotation-core"))
    annotationProcessor(project(":annotation:annotation-processor"))
}

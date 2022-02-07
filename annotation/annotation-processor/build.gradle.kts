// Add plugins
plugins {
    java
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["annotation.id"]}-${extra["annotation.processor.id"]}")
version = extra["annotation.processor.version"] as String

// Add dependencies
dependencies {
    implementation(project(":annotation:annotation-core"))
}

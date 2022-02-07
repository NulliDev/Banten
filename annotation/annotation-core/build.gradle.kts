// Add plugins
plugins {
    java
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["annotation.id"]}-${extra["annotation.core.id"]}")
version = extra["annotation.core.version"] as String

// Add plugins
plugins {
    java
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["annotation.id"]}-${extra["annotation.processor.id"]}")
version = extra["annotation.processor.version"] as String

// Add dependencies
evaluationDependsOn(":annotation:core")
dependencies {
    implementation(project(":annotation:core"))
}

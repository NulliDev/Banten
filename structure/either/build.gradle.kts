// Add plugins
plugins {
    java
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["structure.id"]}-${extra["structure.either.id"]}")
version = extra["structure.either.version"] as String

// Add annotations
evaluationDependsOn(":annotation:core")
evaluationDependsOn(":annotation:processor")
dependencies {
    implementation(project(":annotation:core"))
    annotationProcessor(project(":annotation:processor"))
}

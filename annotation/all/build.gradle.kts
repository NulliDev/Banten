// Add plugins
plugins {
    `java-library`
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["annotation.id"]}-${extra["annotation.all.id"]}")
version = extra["annotation.all.version"] as String

// Add dependencies
evaluationDependsOn(":annotation:core")
evaluationDependsOn(":annotation:processor")
dependencies {
    api(project(":annotation:core"))
    annotationProcessor(project(":annotation:processor"))
}

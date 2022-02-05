// Add plugins
plugins {
    java
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Set project information
base.archivesName.set("${extra["base.id"]}-${extra["structure.id"]}-${extra["structure.either.id"]}")
version = extra["structure.either.version"] as String

// Add dependencies
evaluationDependsOn(":annotation:core")
evaluationDependsOn(":annotation:processor")
dependencies {
    compileOnly(project(":annotation:core"))
    annotationProcessor(project(":annotation:processor"))

    testCompileOnly(project(":annotation:core"))
    testAnnotationProcessor(project(":annotation:processor"))
}

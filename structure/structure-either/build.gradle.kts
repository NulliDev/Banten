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
dependencies {
    compileOnly(project(":annotation:annotation-core"))
    annotationProcessor(project(":annotation:annotation-processor"))

    testCompileOnly(project(":annotation:annotation-core"))
    testAnnotationProcessor(project(":annotation:annotation-processor"))
}

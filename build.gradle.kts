import java.util.Calendar
import org.cadixdev.gradle.licenser.Licenser
import groovy.lang.Tuple3
import groovy.lang.Tuple4
import java.time.Instant
import java.time.format.DateTimeFormatter

// Add plugins
plugins {
    java
    `maven-publish`
    signing
    id("org.cadixdev.licenser")
}

// Cache some properties
internal val artifactGroup: String = extra["base.group"] as String
internal val fileEncoding: String = extra["file.encoding"] as String
internal val jUnitVersion: String = extra["junit.version"] as String
internal val headerFile: TextResource = resources.text.fromFile(rootProject.file("HEADER"))
internal val startYear: String = extra["base.year.start"] as String
internal val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
internal val author: String = extra["base.author"] as String

// Setup Pom Properties
internal val projectName: String = extra["base.name"] as String
internal val projectDescription: String = extra["base.description"] as String

internal val website: String = extra["base.website"] as String
internal val rawRepo: String = extra["base.repository"] as String
internal val urlRepo: String = "https://${rawRepo}"
internal val scmUrlRepo: String = "${urlRepo}.git"
internal val scmConRepo: String = "scm:git:${scmUrlRepo}"
internal val scmDevConRepo: String = "scm:git:${scmUrlRepo}"

internal val issueSystem: String = "GitHub Issues"
internal val issueSite: String = "${urlRepo}/issues"

internal val licenseName: String = extra["base.license.name"] as String
internal val licenseUrl: String = extra["base.license.url"] as String

// Setup Developers and Contributors
internal val developersSimple: List<Tuple4<String, String, String, String>> = listOf(
    Tuple4("ahaim5357", "Aaron Haim", "ahaim@nulli.dev", "https://ahaim.nulli.dev/")
)
internal val contributorsSimple: List<Tuple3<String, String, String>> = listOf()

internal val developers: List<Action<MavenPomDeveloper>> = developersSimple.map {
    Action<MavenPomDeveloper> {
        id.set(it.v1)
        name.set(it.v2)
        email.set(it.v3)
        url.set(it.v4)
    }
} + listOf()

internal val contributors: List<Action<MavenPomContributor>> = contributorsSimple.map {
    Action<MavenPomContributor> {
        name.set(it.v1)
        email.set(it.v2)
        url.set(it.v3)
    }
}  + listOf()

subprojects {
    // Java Settings
    plugins.withType<JavaPlugin> {
        group = artifactGroup
        java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(extra["java.version"] as String))

            withSourcesJar()
            withJavadocJar()
        }
        tasks.withType<JavaCompile>().configureEach {
            options.encoding = fileEncoding
        }
        tasks.javadoc {
            options {
                encoding = fileEncoding
                if (this is StandardJavadocDocletOptions)
                    tags(
                        "apiNote:a:API Note:",
                        "implSpec:a:Implementation Requirements:",
                        "implNote:a:Implementation Note:"
                    )
            }
        }

        // Manifest Attributes
        afterEvaluate {
            tasks.jar {
                manifest.attributes(mapOf(
                    "Specification-Title" to projectName,
                    "Specification-Vendor" to author,
                    "Specification-Version" to (project.version as String).split('-')[0],
                    "Implementation-Title" to base.archivesName.get(),
                    "Implementation-Vendor" to author,
                    "Implementation-Version" to project.version,
                    "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                ))
            }
        }

        // Unit Tests
        repositories {
            mavenCentral()
        }
        tasks.test {
            useJUnitPlatform()
        }
        dependencies {
            testImplementation(platform(mapOf(
                "group" to "org.junit",
                "name" to "junit-bom",
                "version" to jUnitVersion
            )))
            testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
        }

        // Maven Publish Settings
        plugins.withType<MavenPublishPlugin> {
            publishing {

                // Set all publishing settings
                publications.withType<MavenPublication>().all {
                    afterEvaluate {
                        artifactId = base.archivesName.get()
                    }

                    pom {
                        name.set(projectName)
                        description.set(projectDescription)
                        url.set(website)
                        inceptionYear.set(startYear)

                        licenses {
                            license {
                                name.set(licenseName)
                                url.set(licenseUrl)
                            }
                        }

                        developers {
                            developers.forEach(this::developer)
                        }

                        contributors {
                            contributors.forEach(this::contributor)
                        }

                        scm {
                            url.set(scmUrlRepo)
                            connection.set(scmConRepo)
                            developerConnection.set(scmDevConRepo)
                        }

                        issueManagement {
                            system.set(issueSystem)
                            url.set(issueSite)
                        }
                    }
                }

                // Create artifacts
                afterEvaluate {
                    publications.create(base.archivesName.get(), MavenPublication::class.java) {
                        from(components["java"])
                    }
                }
            }

            // Signing Settings
            afterEvaluate {
                if (extra.has("signing.secretKeyRingFile")) {
                    plugins.withType<SigningPlugin> {
                        signing.sign(*publishing.publications.withType<MavenPublication>().toTypedArray())
                    }
                }
            }
        }
    }

    // License Settings
    plugins.withType<Licenser> {
        license {
            header.set(headerFile)
            properties {
                set("startYear", startYear)
                set("currentYear", currentYear)
                set("author", author)
            }
            include("**/*.java")
        }
    }
}

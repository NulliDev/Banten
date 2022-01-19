import java.util.Calendar
import org.cadixdev.gradle.licenser.Licenser

// Add plugins
plugins {
    java
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

allprojects {
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
            options.encoding = fileEncoding
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

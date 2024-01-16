import org.jetbrains.gradle.ext.settings
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.runConfigurations

plugins {
    id("com.gtnewhorizons.retrofuturagradle") version "1.3.26"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("com.github.gmazzo.buildconfig") version "5.3.3"
    id("io.freefair.lombok") version "8.4"
}

group = "dev.redstudio"
version = "1.2-Dev-1" // Versioning must follow Ragnarök versioning convention: https://shor.cz/ragnarok_versioning_convention

val id = "mysticstaffs"

minecraft {
    mcVersion = "1.12.2"
    username = "Desoroxxx"
    extraRunJvmArguments = listOf("-Dforge.logging.console.level=debug")
    javaToolchain.get().vendor.set(JvmVendorSpec.ADOPTIUM)
}

repositories {
    maven {
        name = "Curse Maven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    implementation(rfg.deobf("curse.maven:LLibrary-243298:2504999"))
    implementation(rfg.deobf("curse.maven:MowziesMobs-250498:3048685"))

    implementation(rfg.deobf("curse.maven:ElenaiDodge2-442962:3343308"))
}

buildConfig {
    packageName("${project.group}.${id}")
    className("ProjectConstants")
    documentation.set("This class defines constants for ${project.name}.\n<p>\nThey are automatically updated by Gradle.")

    useJavaOutput()
    buildConfigField("String", "ID", provider { """"${id}"""" })
    buildConfigField("String", "NAME", provider { "\"Mystic Staffs\"" })
    buildConfigField("String", "VERSION", provider { """"${project.version}"""" })
    buildConfigField("org.apache.logging.log4j.Logger", "LOGGER", "org.apache.logging.log4j.LogManager.getLogger(NAME)")
}

idea {
    module {
        inheritOutputDirs = true

        excludeDirs = setOf(
                file(".github"), file(".gradle"), file(".idea"), file("build"), file("gradle"), file("run")
        )
    }

    project {
        settings {
            jdkName = "1.8"
            languageLevel = IdeaLanguageLevel("JDK_1_8")

            runConfigurations {
                create("Client", Gradle::class.java) {
                    taskNames = setOf("runClient")
                }
                create("Server", Gradle::class.java) {
                    taskNames = setOf("runServer")
                }
                create("Obfuscated Client", Gradle::class.java) {
                    taskNames = setOf("runObfClient")
                }
                create("Obfuscated Server", Gradle::class.java) {
                    taskNames = setOf("runObfServer")
                }
                create("Vanilla Client", Gradle::class.java) {
                    taskNames = setOf("runVanillaClient")
                }
                create("Vanilla Server", Gradle::class.java) {
                    taskNames = setOf("runVanillaServer")
                }
            }
        }
    }
}

// Set the toolchain version to decouple the Java we run Gradle with from the Java used to compile and run the mod
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
    withSourcesJar() // Generate sources jar when building and publishing
}

tasks.processResources.configure {
    inputs.property("version", project.version)
    inputs.property("id", id)

    filesMatching("mcmod.info") {
        expand(mapOf("version" to project.version, "id" to id))
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "ModSide" to "CLIENT",
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.isFork = true
    options.forkOptions.jvmArgs = listOf("-Xmx4G")
}

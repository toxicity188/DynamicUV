import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
    id("com.vanniktech.maven.publish") version "0.36.0"
    signing
}

val artifactBaseId = rootProject.name.lowercase()
val artifactVersion = project.version.toString().substringBeforeLast('-')

signing {
    useGpgCmd()
}

dependencies {
    implementation("org.jetbrains:annotations:26.1.0")
    listOf(
        "com.google.code.gson:gson:2.13.2",
        "it.unimi.dsi:fastutil:8.5.18"
    ).forEach {
        compileOnly(it)
        testImplementation(it)
    }
    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    jar {
        archiveBaseName = rootProject.name
    }
    test {
        useJUnitPlatform()
    }
}

mavenPublishing  {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.toxicity188", artifactBaseId, artifactVersion)
    configure(JavaLibrary(
        javadocJar = JavadocJar.Javadoc(),
        sourcesJar = SourcesJar.Sources(),
    ))
    pom {
        name = artifactBaseId
        description = "A simple library for Minecraft Java Edition models with in-game configurable textures."
        inceptionYear = "2025"
        url = "https://github.com/toxicity188/DynamicUV/"
        licenses {
            license {
                name = "MIT License"
                url = "https://mit-license.org/"
            }
        }
        developers {
            developer {
                id = "toxicity188"
                name = "toxicity188"
                url = "https://github.com/toxicity188/"
            }
        }
        scm {
            url = "https://github.com/toxicity188/DynamicUV/"
            connection = "scm:git:git://github.com/toxicity188/DynamicUV.git"
            developerConnection = "scm:git:ssh://git@github.com/toxicity188/DynamicUV.git"
        }
    }
}

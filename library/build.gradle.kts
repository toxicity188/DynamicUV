import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar
import kotlin.io.encoding.Base64

plugins {
    alias(libs.plugins.conventions.standard)
    id("com.vanniktech.maven.publish") version "0.37.0"
    signing
}

val artifactBaseId = rootProject.name.lowercase()
val artifactVersion = project.version.toString()

signing {
    val key = System.getenv("SIGNING_KEY")?.let {
        Base64.decode(it.toByteArray()).toString(Charsets.UTF_8)
    }
    val password = System.getenv("SIGNING_PASSWORD")
    if (!key.isNullOrEmpty() && !password.isNullOrEmpty()) {
        useInMemoryPgpKeys(
            key,
            password
        )
    } else useGpgCmd()
}

dependencies {
    implementation("org.jetbrains:annotations:26.1.0")
    listOf(
        "com.google.code.gson:gson:2.14.0",
        "it.unimi.dsi:fastutil:8.5.19"
    ).forEach {
        compileOnly(it)
        testImplementation(it)
    }
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
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

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/toxicity188/DynamicUV")
            credentials {
                username = providers.environmentVariable("GITHUB_ACTOR").orNull
                password = providers.environmentVariable("PACKAGES_API_TOKEN").orNull
            }
        }
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

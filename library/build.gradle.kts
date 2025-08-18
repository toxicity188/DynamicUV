plugins {
    `maven-publish`
}

dependencies {
    implementation("org.jetbrains:annotations:26.0.2")
    listOf(
        "com.google.code.gson:gson:2.13.1",
        "it.unimi.dsi:fastutil:8.5.16"
    ).forEach {
        compileOnly(it)
        testImplementation(it)
    }
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
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
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
dependencies {
    implementation("org.jetbrains:annotations:26.0.2")
    listOf(
        "com.google.code.gson:gson:2.13.1",
        "it.unimi.dsi:fastutil:8.5.15"
    ).forEach {
        compileOnly(it)
        testImplementation(it)
    }
}

tasks.jar {
    archiveBaseName = rootProject.name
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
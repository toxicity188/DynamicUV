plugins {
    alias(libs.plugins.conventions.standard)
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.24"
    id("xyz.jpenilla.run-paper") version "3.1.0"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.4.0"
    id("com.gradleup.shadow") version "9.6.1"
}

val testVersion = "26.3"

dependencies {
    paperweight.paperDevBundle("$testVersion.build.+")
    implementation(project(":library"))
}

tasks.runServer {
    version(testVersion)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

bukkitPluginYaml {
    main = "kr.toxicity.library.dynamicuv.test.TestPlugin"
    version = project.version.toString()
    name = "DynamicUV-TestPlugin"
    foliaSupported = true
    apiVersion = "1.20"
    author = "toxicity"
    description = "Test plugin of dynamic uv."
}

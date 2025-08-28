plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.3.0"
    id("com.gradleup.shadow") version "9.0.2"
}

val testVersion = "1.21.8"

dependencies {
    paperweight.paperDevBundle("$testVersion-R0.1-SNAPSHOT")
    implementation(project(":library"))
}

tasks.runServer {
    version(testVersion)
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
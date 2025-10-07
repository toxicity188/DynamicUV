plugins {
    java
}

allprojects {
    apply(plugin = "java")

    group = "kr.toxicity.library.dynamicuv"
    version = "1.0.8"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.42")
        annotationProcessor("org.projectlombok:lombok:1.18.42")

        testCompileOnly("org.projectlombok:lombok:1.18.42")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
        }
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }
}


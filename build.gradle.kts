plugins {
    id("java")
}

allprojects {
    apply(plugin = "java")

    group = "kr.toxicity.library.dynamicuv"
    version = "1.0.4"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")

        testCompileOnly("org.projectlombok:lombok:1.18.38")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        compileJava {
            options.encoding = Charsets.UTF_8.name()
        }
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }
}


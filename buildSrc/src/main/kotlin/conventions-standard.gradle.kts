plugins {
    java
}

group = "kr.toxicity.library.dynamicuv"
version = property("project_version").toString()

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
}

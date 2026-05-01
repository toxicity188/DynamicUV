import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

val Project.libs
    get() = rootProject.extensions.getByName("libs") as LibrariesForLibs

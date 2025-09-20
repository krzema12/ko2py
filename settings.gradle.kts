rootProject.name = "ko2py"

include("compiler-plugin")
include("gradle-plugin")
include("playground")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

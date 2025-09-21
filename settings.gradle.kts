rootProject.name = "ko2py"

include("compiler-plugin")
include("compiler-plugin:ast")
include("compiler-plugin:ast:generator")
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

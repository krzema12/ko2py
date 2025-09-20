plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
}

gradlePlugin {
    plugins {
        create("ko2pyGradlePlugin") {
            id = "it.krzeminski.ko2py.gradle"
            implementationClass = "it.krzeminski.ko2py.gradle.Ko2pyPlugin"
        }
    }
}

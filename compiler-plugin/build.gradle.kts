plugins {
    kotlin("jvm")
    id("maven-publish")
    id("io.kotest")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":compiler-plugin:ast"))
    compileOnly(kotlin("compiler"))

    testImplementation("io.kotest:kotest-assertions-core:6.0.3")
    testImplementation("io.kotest:kotest-framework-engine:6.0.3")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.3")
    testImplementation("dev.zacsweers.kctfork:core:0.9.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi"
        )
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

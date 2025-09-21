plugins {
    kotlin("jvm")
    id("io.kotest") version "6.0.3"
}

dependencies {
    testImplementation("io.kotest:kotest-assertions-core:6.0.3")
    testImplementation("io.kotest:kotest-framework-engine:6.0.3")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.3")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

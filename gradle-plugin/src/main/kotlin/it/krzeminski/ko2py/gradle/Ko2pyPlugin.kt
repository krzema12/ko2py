package it.krzeminski.ko2py.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class Ko2pyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.dependencies.add(
            "kotlinCompilerPluginClasspath",
            "it.krzeminski.ko2py:compiler-plugin:0.0.1-SNAPSHOT"
        )
    }
}

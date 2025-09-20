package it.krzeminski.ko2py.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class Ko2pyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("ko2py", Ko2pyExtension::class.java)

        project.dependencies.add(
            "kotlinCompilerPluginClasspath",
            "it.krzeminski.ko2py:compiler-plugin:0.0.1-SNAPSHOT"
        )

        project.tasks.withType(KotlinCompile::class.java).configureEach { task ->
            task.compilerOptions {
                val outputDir = extension.outputDir ?: project.layout.buildDirectory.asFile.get().also { buildDir ->
                    extension.outputDir = buildDir
                }
                freeCompilerArgs.addAll("-P", "plugin:ko2py:outputDir=$outputDir")
            }
        }
    }
}

open class Ko2pyExtension {
    var outputDir: File? = null
}

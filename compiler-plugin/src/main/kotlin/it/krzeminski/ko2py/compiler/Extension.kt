package it.krzeminski.ko2py.compiler

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import java.io.File

class Extension(private val outputDir: File) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        // Fake implementation, replace it with the real thing:
        outputDir.resolve("dummy-output.txt").also {
            it.parentFile.mkdirs()
            it.writeText("Hello from my first Kotlin compiler plugin!")
        }
    }
}

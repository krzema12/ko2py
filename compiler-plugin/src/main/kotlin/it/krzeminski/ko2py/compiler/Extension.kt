package it.krzeminski.ko2py.compiler

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import topython.toPython
import java.io.File

class Extension(private val outputDir: File, private val messageCollector: MessageCollector) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        val visitor = Visitor()
        val ast = moduleFragment.accept(visitor, Unit)
        val generatedPython = ast.toPython()
        // Fake implementation, replace it with the real thing:
        outputDir.resolve("output.py").also {
            it.parentFile.mkdirs()
            it.writeText(generatedPython)
        }
        messageCollector.report(CompilerMessageSeverity.INFO, "Generated Python code saved to ${outputDir.absolutePath}")
    }
}

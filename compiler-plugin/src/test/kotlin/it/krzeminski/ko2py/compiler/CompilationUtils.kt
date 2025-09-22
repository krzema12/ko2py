package it.krzeminski.ko2py.compiler

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import java.io.File
import kotlin.collections.plus

fun compileToPython(input: String): JvmCompilationResult {
    return KotlinCompilation().apply {
        sources = listOf(SourceFile.kotlin("main.kt", input))
        compilerPluginRegistrars = listOf(Registrar())
        commandLineProcessors = listOf(ArgsProcessor())
        inheritClassPath = true
        verbose = false
        messageOutputStream = System.out
        kotlincArguments += listOf(
            "-P", "plugin:com.tschuchort.compiletesting.maincommandlineprocessor:ko2py:outputDir=build",
        )
    }.compile()
}

fun JvmCompilationResult.getPythonOutput() =
   this.findGeneratedOutputDir().resolve("output.py").readText()

private fun JvmCompilationResult.findGeneratedOutputDir(): File {
    val pattern = "Generated Python code saved to (.+)".toRegex()
    val filePath = pattern.find(this.messages)?.groups[1]?.value
        ?: error("Couldn't find output file from messages")
    return File(filePath)
}

package it.krzeminski.ko2py.compiler

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.File

class KotlinToPythonTest : FunSpec({
    test("smoke test") {
        // language=kotlin
        val input = """
            fun main() {
                println("Hello, world!")
            }
        """.trimIndent()

        val actualOutput = compileToPython(input)

        actualOutput shouldBe """
            def main():
                print("Hello, world!")

            if __name__ == "__main__":
                main()

        """.trimIndent()
    }
})

private fun compileToPython(input: String): String {
    val result = KotlinCompilation().apply {
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
    return result.findGeneratedOutputDir().resolve("output.py").readText()
}

private fun JvmCompilationResult.findGeneratedOutputDir(): File {
    val pattern = "Generated Python code saved to (.+)".toRegex()
    val filePath = pattern.find(this.messages)?.groups[1]?.value ?: error("Couldn't find output file from messages")
    return File(filePath)
}

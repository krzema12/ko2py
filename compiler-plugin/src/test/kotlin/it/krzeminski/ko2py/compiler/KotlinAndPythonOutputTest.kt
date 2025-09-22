package it.krzeminski.ko2py.compiler

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe

class KotlinAndPythonOutputTest : FunSpec({
    test("smoke test") {
        // language=kotlin
        val input = """
            fun main() {
                println("Hello, world!")
            }
        """.trimIndent()

        val compilationResult = compileToPython(input)
        val jvmClassFile = compilationResult.generatedFiles
            .first { it.extension == "class" }
            .parentFile
        val pythonCodeString = compilationResult.getPythonOutput()
        val pythonCode = tempfile()
        pythonCode.writeText(pythonCodeString)
        val stdoutFromJvm = runCommand("java", "-cp", jvmClassFile.absolutePath, "MainKt")
        val stdoutFromPython = runCommand("python3", pythonCode.absolutePath)

        val expectedStdout = "Hello, world!\n"

        stdoutFromPython shouldBe expectedStdout
        stdoutFromJvm shouldBe expectedStdout
    }
})

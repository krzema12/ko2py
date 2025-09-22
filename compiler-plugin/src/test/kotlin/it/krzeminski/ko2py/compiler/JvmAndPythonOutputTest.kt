package it.krzeminski.ko2py.compiler

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe

class JvmAndPythonOutputTest : FunSpec({
    // TODO: iterate over all Kotlin files in resources
    test("smoke test") {
        val input = KotlinToPythonTest::class.java.getResource("/HelloWorld.kt").readText()

        val compilationResult = compileToPython(input)
        val jvmClassFile = compilationResult.generatedFiles
            .first { it.extension == "class" }
            .parentFile
        val pythonCodeString = compilationResult.getPythonOutput()
        val pythonCode = tempfile()
        pythonCode.writeText(pythonCodeString)
        val stdoutFromJvm = runCommand("java", "-cp", jvmClassFile.absolutePath, "MainKt")
        val stdoutFromPython = runCommand("python3", pythonCode.absolutePath)

        val expectedStdout = input
            .substringAfter("// Output:\n")
            .lines().joinToString(separator = "\n") { it.removePrefix("//").trimStart() }

        stdoutFromPython shouldBe expectedStdout
        stdoutFromJvm shouldBe expectedStdout
    }
})

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

        val actualOutput = compileToPython(input).getPythonOutput()
        val pythonCode = tempfile()
        pythonCode.writeText(actualOutput)
        val stdoutFromPython = runCommand("python3", pythonCode.absolutePath)

        val expectedStdout = "Hello, world!\n"

        stdoutFromPython shouldBe expectedStdout
    }
})
